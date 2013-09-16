// Copyright 2013 Cloudera Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cloudera.impala.authorization;

import java.util.EnumSet;
import java.util.List;

import org.apache.commons.lang.reflect.ConstructorUtils;
import org.apache.sentry.core.Authorizable;
import org.apache.sentry.provider.file.ResourceAuthorizationProvider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/*
 * Class used to check whether a user has access to a given resource.
 */
public class AuthorizationChecker {
  private final ResourceAuthorizationProvider provider;
  private final AuthorizationConfig config;

  /*
   * Creates a new AuthorizationChecker based on the config values.
   */
  public AuthorizationChecker(AuthorizationConfig config) {
    Preconditions.checkNotNull(config);
    this.config = config;
    if (config.isEnabled()) {
      this.provider = createAuthorizationProvider(config);
      Preconditions.checkNotNull(provider);
    } else {
      this.provider = null;
    }
  }

  /*
   * Creates a new ResourceAuthorizationProvider based on the given configuration.
   */
  private static ResourceAuthorizationProvider
      createAuthorizationProvider(AuthorizationConfig config) {
    try {
      // Try to create an instance of the specified policy provider class.
      // Re-throw any exceptions that are encountered.
      return (ResourceAuthorizationProvider) ConstructorUtils.invokeConstructor(
          Class.forName(config.getPolicyProviderClassName()),
          new Object[] {config.getPolicyFile(), config.getServerName()});
    } catch (Exception e) {
      // Re-throw as unchecked exception.
      throw new IllegalStateException(
          "Error creating ResourceAuthorizationProvider: " + e.getMessage(), e);
    }
  }

  /*
   * Returns the configuration used to create this AuthorizationProvider.
   */
  public AuthorizationConfig getConfig() {
    return config;
  }

  /*
   * Returns true if the given user has permission to execute the given
   * request, false otherwise. Always returns true if authorization is disabled.
   */
  public boolean hasAccess(User user, PrivilegeRequest request) {
    Preconditions.checkNotNull(user);
    Preconditions.checkNotNull(request);

    // If authorization is not enabled the user will always have access. If this is
    // an internal request, the user will always have permission.
    if (!config.isEnabled() || user instanceof ImpalaInternalAdminUser) {
      return true;
    }

    EnumSet<org.apache.sentry.core.Action> actions =
        request.getPrivilege().getHiveActions();

    List<Authorizable> authorizeables = Lists.newArrayList();
    authorizeables.add(new org.apache.sentry.core.Server(config.getServerName()));
    // If request.getAuthorizeable() is null, the request is for server-level permission.
    if (request.getAuthorizeable() != null) {
      authorizeables.addAll(request.getAuthorizeable().getHiveAuthorizeableHierarchy());
    }

    // The Hive Access API does not currently provide a way to check if the user
    // has any privileges on a given resource.
    if (request.getPrivilege().getAnyOf()) {
      for (org.apache.sentry.core.Action action: actions) {
        if (provider.hasAccess(new org.apache.sentry.core.Subject(user.getShortName()),
            authorizeables, EnumSet.of(action))) {
          return true;
        }
      }
      return false;
    }
    return provider.hasAccess(new org.apache.sentry.core.Subject(user.getShortName()),
        authorizeables, actions);
  }
}

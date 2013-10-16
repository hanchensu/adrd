// Copyright 2012 Cloudera Inc.
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

#ifndef IMPALA_TESTUTIL_IN_PROCESS_SERVERS_H
#define IMPALA_TESTUTIL_IN_PROCESS_SERVERS_H

#include <boost/scoped_ptr.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/thread.hpp>

#include "common/status.h"

namespace impala {

class ImpalaServer;
class ExecEnv;
class ThriftServer;
class Webserver;
class Metrics;
class StateStore;

// A single impala service, with a backend server, two client servers,
// a webserver and optionally a connection to a state-store.
//
// TODO: Static StartCluster method which runs one or more
// ImpalaServer(s) and an optional StateStore.
class InProcessImpalaServer {
 public:
  // Initialises the server, but does not start any network-attached
  // services or run any threads.
  InProcessImpalaServer(const std::string& hostname, int backend_port,
                        int subscriber_port, int webserver_port,
                        const std::string& statestore_host, int statestore_port);

  // Starts all servers, including the beeswas and hs2 client
  // servers. If use_statestore is set, a connection to the statestore
  // is established. If there is no error, returns Status::OK.
  Status StartWithClientServers(int beeswax_port, int hs2_port, bool use_statestore);

  // Starts only the backend server; useful when running a cluster of
  // InProcessImpalaServers and only one is to serve client requests.
  Status StartAsBackendOnly(bool use_statestore);

  // Blocks until the backend server exits. Returns Status::OK unless
  // there was an error joining.
  Status Join();

  ImpalaServer* impala_server() { return impala_server_.get(); }

 private:
  // Hostname for this server, usually FLAGS_hostname
  const std::string hostname_;

  // Port to start the backend server on
  const uint32_t backend_port_;

  // The ImpalaServer that handles client and backend requests
  boost::scoped_ptr<ImpalaServer> impala_server_;

  // ExecEnv holds much of the per-service state
  boost::scoped_ptr<ExecEnv> exec_env_;

  // Backend Thrift server
  boost::scoped_ptr<ThriftServer> be_server_;

  // Frontend HiveServer2 server
  boost::scoped_ptr<ThriftServer> hs2_server_;

  // Frontend Beeswax server.
  boost::scoped_ptr<ThriftServer> beeswax_server_;

};

// An in-process state-store, with webserver and metrics.
class InProcessStateStore {
 public:
  // Constructs but does not start the state-store.
  InProcessStateStore(int state_store_port, int webserver_port);

  // Starts the state-store server, and the processing thread.
  Status Start();

  uint32_t port() { return state_store_port_; }

 private:
  // Websever object to serve debug pages through.
  boost::scoped_ptr<Webserver> webserver_;

  // Metrics object
  boost::scoped_ptr<Metrics> metrics_;

  // Port to start the state-store on.
  uint32_t state_store_port_;

  // The state-store instance
  boost::scoped_ptr<StateStore> state_store_;

  // State-store Thrift server
  boost::scoped_ptr<ThriftServer> state_store_server_;

  boost::scoped_ptr<boost::thread> state_store_main_loop_;
};

}

#endif

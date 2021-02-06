import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Landing } from "./components/Langing";
import { Login } from "./components/Login";
import { Home } from "./components/Home";
import { NavigationBar } from "./components/NavigationBar";
import { NoMatch } from "./components/NoMatch";
import { Layout } from "./components/Layout";

export const App = () => (
  <React.Fragment>
    <Layout>
      <Router>
        <Switch>
          <Route exact path="/" component={Landing}></Route>
          <Route path="/login" component={Login}></Route>
          <Route path="/home" component={Home}></Route>
          <Route component={NoMatch}></Route>
        </Switch>
      </Router>
    </Layout>
  </React.Fragment>
);

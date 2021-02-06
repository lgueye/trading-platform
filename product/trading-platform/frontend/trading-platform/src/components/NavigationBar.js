import React from "react";
import { Nav, NavBar } from "react-bootstrap";
import styled from "styled-components";

const Styles = styled.div`
  .navbar { 
    background-color: #222;
  }
  
  .navbar-brand .navbar-nav .nav-link { 
    color: #bbb;
    &:hover {
      color: white;
    }
  }
  .navbar-brand {}
`

export const NavigationBar = () => (
  <div>
    <h1>NAV BAR</h1>
  </div>
);

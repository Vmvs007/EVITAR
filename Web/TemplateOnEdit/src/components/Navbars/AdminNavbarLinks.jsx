/*!

=========================================================
* Light Bootstrap Dashboard React - v1.3.0
=========================================================

* Product Page: https://www.creative-tim.com/product/light-bootstrap-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/light-bootstrap-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React, { Component } from "react";
import { withRouter } from "react-router";
import { Nav, NavDropdown, MenuItem } from "react-bootstrap";
import AuthService from "../Authentication/AuthService.js";
class AdminNavbarLinks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      width: window.innerWidth
    };

    this.Auth = new AuthService();
  }

  sourceSelected = () => {
    this.Auth.logout();
  };

  render() {
    return (
      <div>
        <Nav pullRight>
          <NavDropdown
            eventKey={2}
            title="Account"
            id="basic-nav-dropdown-right"
          >
            <MenuItem onClick={() => this.props.history.push("/admin/user")}>
              Profile
            </MenuItem>
            <MenuItem divider />
            <MenuItem onClick={this.sourceSelected}>Logout</MenuItem>
          </NavDropdown>
        </Nav>
      </div>
    );
  }
}

export default withRouter(AdminNavbarLinks);

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
import { Grid, Row, Col } from "react-bootstrap";
import Moment from "moment";
import { Card } from "components/Card/Card.jsx";
import AuthService from "components/Authentication/AuthService.js";
import {Link } from "react-router-dom";
class EpiDetails extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      epiInfo: {},
      isLoading1: false
    };
  }
  componentDidMount() {
    let id = this.props.match.params.id;
    this.setState({ isLoading1: true });
    this.Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/epi/view/" + id,
      {
        method: "GET"
      }
    )
      .then(result => {
        this.setState({
          epiInfo: result[0],
          isLoading1: false
        });
      })
      .catch(error => alert("Error! " + error.message));
  }
  render() {
    if (this.state.isLoading1) {
      return (
        <div className="content">
          <i className="fa fa-spinner fa-spin fa-3x"></i>
          <p>isLoading...</p>
        </div>
      );
    }
    return (
      <div className="content">
        <Grid fluid>
          <Row>
            <Col md={6}>
              <Card
                content={
                  <Col md={12}>
                <Card
                  content={
                  <div>
                    <h3>{this.state.epiInfo.nomeEPI}</h3>
                    <Col md={4}>
                      <p>Id:</p>
                      <p>Register Date:</p>
                      <p>Date Expire:</p>
                      <p>Inspector:</p>
                      <p>Valid:</p>
                      <p>Epi Type:</p>
                    </Col>
                    <Col md={8}>
                      <p>{this.state.epiInfo.idEPI}</p>
                      <p>
                        {Moment(this.state.epiInfo.dataRegistoEPI).format(
                          "DD-MM-YYYY"
                        )}
                      </p>
                      <p>
                        {Moment(
                          this.state.epiInfo.dataValidadeEPI
                        ).format("DD-MM-YYYY")}
                      </p>
                      <p>{this.state.epiInfo.nomeInspector}</p>
                      <p>{this.state.epiInfo.valido===0?("NOT"):("YES")}</p>
                      <p>{this.state.epiInfo.nomeTipoEPI}</p>
                      <Link to={"editor/"+this.props.match.params.id}>Edit</Link>
                    </Col>
                  </div>
                  }
                  />
                  </Col>
                }
              />
            </Col>
          </Row>
        </Grid>
      </div>
    );
  }
}

export default EpiDetails;

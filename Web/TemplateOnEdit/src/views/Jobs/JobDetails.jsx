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
import { Card } from "components/Card/Card.jsx";
import AuthService from "components/Authentication/AuthService.js";
import {Link } from "react-router-dom";
class EpiDetails extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      jobInfo: {},
      isLoading1: false,
      isLoading2: false,
      epis: []
    };
  }
  componentDidMount() {
    let id = this.props.match.params.id;
    this.setState({ isLoading1: true, isLoading2: false });
    this.Auth.fetch("https://evitarv2.azurewebsites.net/api/cargo/" + id, {
      method: "GET"
    })
      .then(result => {
        this.setState({
          jobInfo: result,
          isLoading1: false
        });
        console.log(result);
      })
      .catch(error => alert("Error! " + error.message));
    this.Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/epicargo/epis/" + id,
      {
        method: "GET"
      }
    )
      .then(res => {
        let ola = [];
        
        res.map((element, key) => {
          ola[key] = element["nomeTipoEpi"];

          return null;
        });
        console.log(ola)
        this.setState({
          epis: ola,
          isLoading2: false
        });
        
      })
      .catch(error => alert("Error! 1 " + error));
  }
  render() {
    if (this.state.isLoading1 || this.state.isLoading2) {
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
                  <div>
                    <h3>{this.state.jobInfo.nomeCargo}</h3>
                    <Col md={4}>
                      <p>Id:</p>
                      <p>Zone:</p>
                      <p>EPIS:</p>
                    </Col>
                    <Col md={8}>
                      <p>{this.state.jobInfo.idCargo}</p>
                      <p>{this.state.jobInfo.zonaCargo}</p>
                      {this.state.epis.map((element,key)=>{
                        return (<p>{element}</p>)
                      })}
                      <td><Link to={"editor/"+this.state.jobInfo.idCargo}>Edit</Link></td>
                    </Col>
                  </div>
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

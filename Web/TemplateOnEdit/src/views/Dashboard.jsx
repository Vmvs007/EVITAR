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

import { Card } from "components/Card/Card.jsx";
import { StatsCard } from "components/StatsCard/StatsCard.jsx";
import { Grid, Row, Col, Table } from "react-bootstrap";

import AuthService from "../components/Authentication/AuthService.js";
import Moment from "moment";
class Dashboard extends Component {
  state = {
    thArray: ["Employee ID", "Name", "Date", "EPI Check"],
    ola: "coisaBoa",
    startDate: new Date(),
    cards: {},
    isLoading1: false,
    isLoading2: false,
    data: []
  };

  componentDidMount() {
    this.setState({ isLoading1: true, isLoading2: true });
    const Auth = new AuthService();
    Auth.fetch(
      "https://evitar.azurewebsites.net/api/movimento/stats/" +
        Moment(this.state.startDate).format("YYYY-MM-DD"),
      {
        method: "GET"
      }
    )
      .then(result => {
        this.setState({
          cards: result,
          isLoading1: false
        });
      })
      .catch(error => alert("Error! " + error.message));
    Auth.fetch(
      "https://evitar.azurewebsites.net/api/movimento/entradas/" +
        Moment(this.state.startDate).format("YYYY-MM-DD"),
      {
        method: "GET"
      }
    )
      .then(result => {
        this.setState({
          data: result,
          isLoading2: false
        });
      })
      .catch(error => alert("Error! " + error.message));
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
      <div className="content" >
        <Grid fluid>
          <Row>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-id text-warning" />}
                statsText="Employee Attendance"
                statsValue={this.state.cards.movimentosDiarios}
                statsIcon={<i className="fa fa-clock-o" />}
                statsIconText="Day"
              />
            </Col>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-id text-success" />}
                statsText="Employee Attendance"
                statsValue={this.state.cards.alertasDiarios}
                statsIcon={<i className="fa fa-calendar-o" />}
                statsIconText="Week"
              />
            </Col>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-global text-danger" />}
                statsText="EPI Warnings"
                statsValue={this.state.cards.alertasDiarios}
                statsIcon={<i className="fa fa-clock-o" />}
                statsIconText="Day"
              />
            </Col>

          </Row>

          <Row>
            <Col md={12}>
            <Card
                title="Archive"
                category="Here is a subtitle for this table"
                ctTableFullWidth
                ctTableResponsive
                content={
                  <div className="tabela">
                    <Table striped hover>
                      <thead>
                        <tr>
                          {this.state.thArray.map((prop, key) => {
                            return <th key={key}>{prop}</th>;
                          })}
                        </tr>
                      </thead>
                      <tbody>
                        {this.state.data.map((prop, key) => {
                          return (
                            <tr key={key}>
                              <td>{prop["idColaborador"]}</td>
                              <td>
                                {prop["primeiroNomeCol"] +
                                  " " +
                                  prop["ultimoNomeCol"]}
                              </td>
                          <td>{Moment(prop["dataHora"]).format(
                                  "DD-MM-YYYY HH:mm"
                                )}</td>
                                {prop['check']===0 ? <td style={{ backgroundColor: 'red' }}>ALERT</td> : <td style={{ backgroundColor: 'green' }}>Check</td>}
                            </tr>
                          );
                        })}
                      </tbody>
                    </Table>
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

export default Dashboard;

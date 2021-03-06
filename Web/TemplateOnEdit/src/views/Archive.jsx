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
import { Grid, Row, Col, Table } from "react-bootstrap";

import Card from "components/Card/Card.jsx";
import { StatsCard } from "components/StatsCard/StatsCard.jsx";
import Popup from "reactjs-popup";
import DatePicker from "react-datepicker";
//import Button from "components/CustomButton/CustomButton.jsx";
import "react-datepicker/dist/react-datepicker.css";
import AuthService from "../components/Authentication/AuthService.js";
import Moment from "moment";
class TableList extends Component {
  state = {
    thArray: ["Employee ID", "Name", "Date", "EPI Check"],
    ola: "coisaBoa",
    startDate: new Date(),
    cards: {},
    isLoading1: false,
    isLoading2: false,
    isLoading3: false,
    data: [],
    modalData: []
  };

  handleChange = date => {
    this.setState({
      startDate: date,
      ola: date.toString(),
      isLoading1: true,
      isLoading2: true
    });
    const Auth = new AuthService();
    Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/movimento/stats/" +
        Moment(date).format("YYYY-MM-DD"),
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
      "https://evitarv2.azurewebsites.net/api/movimento/entradas/" +
        Moment(date).format("YYYY-MM-DD"),
      {
        method: "GET"
      }
    )
      .then(result => {
        console.log(result + "ola");
        this.setState({
          data: result,
          isLoading2: false
        });
      })
      .catch(error => alert("Error! " + error.message));
  };
  search = id => {
    console.log(id);
    this.setState({ isLoading3: true });
    const Auth = new AuthService();
    Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/movepi/epiwarningmov/" + id,
      {
        method: "GET"
      }
    )
      .then(result => {
        this.setState({
          modalData: result,
          isLoading3: false
        });
      })
      .catch(error => alert("Error! " + error.message));
  };
  componentDidMount() {
    this.setState({ isLoading1: true, isLoading2: true });
    const Auth = new AuthService();
    Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/movimento/stats/" +
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
      "https://evitarv2.azurewebsites.net/api/movimento/entradas/" +
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
      <div className="content">
        <Grid fluid>
          <Row>
            <Col md={4} sm={4}>
              <Card
                title="Archive"
                content={
                  <DatePicker
                    dateFormat="yyyy/MM/dd"
                    selected={this.state.startDate}
                    onChange={this.handleChange}
                    maxDate={Moment().toDate()}
                  />
                }
              />
            </Col>
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
                              <td>
                                {Moment(prop["dataHora"]).format(
                                  "DD-MM-YYYY HH:mm"
                                )}
                              </td>
                              {prop["check"] === 0 ? (
                                <td style={{ backgroundColor: "red" }}>
                                  <Popup
                                    trigger={
                                      <button style={{backgroundColor:"white",marginLeft:"33%",color:"black"}} className="btn btn-default">ALERT</button>
                                    }
                                    className="modal"
                                    modal
                                    closeOnDocumentClick
                                    onOpen={() =>
                                      this.search(prop["idMovimento"])
                                    }
                                  >
                                    {this.state.isLoading3 ? (
                                      <p>Loading</p>
                                    ) : (
                                      <div style={{ padding: 20 }}>
                                        <h5>Falta:</h5>
                                        <div className="modala">
                                          {this.state.modalData.map(
                                            (propes, key) => {
                                              return (
                                                <p>{propes["nomeTipoEPI"]}</p>
                                              );
                                            }
                                          )}
                                        </div>
                                      </div>
                                    )}
                                  </Popup>
                                </td>
                              ) : (
                                <td
                                  style={{
                                    backgroundColor: "green",
                                    color: "white",
                                    textAlign:"center"
                                  }}
                                >
                                  Check
                                </td>
                              )}
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

export default TableList;

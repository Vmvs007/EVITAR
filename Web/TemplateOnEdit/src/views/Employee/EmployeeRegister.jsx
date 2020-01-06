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
import { Grid, Row, Col, ControlLabel } from "react-bootstrap";
import { Card } from "components/Card/Card.jsx";
import { FormInputs } from "components/FormInputs/FormInputs.jsx";
import Button from "components/CustomButton/CustomButton.jsx";
import DatePicker from "react-datepicker";
import moment from "moment";
import { withRouter } from 'react-router';
import AuthService from "components/Authentication/AuthService.js";
//import axios from 'axios';
class EmployeeRegister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      isLoading: false,
      formFields: {
        nomeColaborador: "",
        primeiroNomeCol: "",
        ultimoNomeCol: "",
        dataNasc: moment()
          .subtract(18, "y")
          .toDate(),
        generoCol: "M",
        ccColaborador: "",
        nifColaborador: "",
        telefoneCol: "",
        emailCol: "",
        moradaCol: "",
        idCargo: 2,
        dataRegistoCol: new Date()
      }
    };
  }
  componentDidMount() {
    this.setState({ isLoading: true });
    const Auth = new AuthService();
    Auth.fetch("https://evitar.azurewebsites.net/api/Cargo", {
      method: "GET"
    })
      .then(result =>
        this.setState({
          data: result,
          isLoading: false
        })
      )
      .catch(error => alert("Error! " + error.message));
  }
  inputChangeHandler = e => {
    let formFields = { ...this.state.formFields };
    if (
      e.target.name === "ccColaborador" ||
      e.target.name === "nifColaborador" ||
      e.target.name === "telefoneCol" ||
      e.target.name === "idCargo"
    )
      formFields[e.target.name] = parseInt(e.target.value);
    else formFields[e.target.name] = e.target.value;
    this.setState({
      formFields
    });
  };
  handleChange = date => {
    let formFields = { ...this.state.formFields };
    formFields["dataNasc"] = date;

    this.setState({
      formFields
    });
  };
  handleSubmit = data => {
    const Auth = new AuthService();
    Auth.fetch("https://evitar.azurewebsites.net/api/Colaborador", {
      method: "POST",
      body: JSON.stringify(data)
    })
      .then(res => {
        if (data.idCargo === 2 || data.idCargo === 3 || data.idCargo === 4) {
          Auth.fetch("https://evitar.azurewebsites.net/Users/register", {
            method: "POST",
            body: JSON.stringify({
              username: String(res["idColaborador"]),
              password: "password",
              idColaborador: res["idColaborador"]
            })
          })
            .then(resa => {
              alert(
                "Login do utilizador:" +
                  data.nomeColaborador +
                  " é : " +
                  res["idColaborador"] +
                  " \n e a password: password"
              );
        this.props.history.push("/admin/employees/");
            })
            .catch(error => alert("Error! 2" + error));
        }
      })
      .catch(error => alert("Error! 1 " + error));
  };
  render() {
    if (this.state.isLoading) {
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
            <Col md={12}>
              <Card
                title="Register Employee"
                content={
                  <form>
                    <FormInputs
                      ncols={["col-md-5", "col-md-3", "col-md-4"]}
                      properties={[
                        {
                          name: "nomeColaborador",
                          label: "Full Name",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Full Name",
                          required: true,
                          onChange: this.inputChangeHandler
                        },
                        {
                          name: "primeiroNomeCol",
                          label: "First Name",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "First Name",
                          required: true,
                          onChange: this.inputChangeHandler
                        },
                        {
                          name: "ultimoNomeCol",
                          label: "Last Name",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Last Name",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />

                    <ControlLabel htmlFor="datepick">Birth Date</ControlLabel>
                    <DatePicker
                      name="dataNasc"
                      id="datepick"
                      label="Birth"
                      dateFormat="yyyy/MM/dd"
                      selected={this.state.formFields.dataNasc}
                      onChange={this.handleChange}
                      maxDate={moment()
                        .subtract(18, "y")
                        .toDate()}
                      className={"form-control"}
                    />
                    <br></br>
                    <ControlLabel>Gender</ControlLabel>
                    <div>
                      <select
                        name="generoCol"
                        className="form-control"
                        onChange={this.inputChangeHandler}
                      >
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                      </select>
                    </div>
                    <br></br>
                    <FormInputs
                      ncols={["col-md-6", "col-md-6"]}
                      properties={[
                        {
                          name: "ccColaborador",
                          label: "CC",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "CC",
                          required: true,
                          onChange: this.inputChangeHandler
                        },
                        {
                          name: "nifColaborador",
                          label: "NIF",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "NIF",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <FormInputs
                      ncols={["col-md-4", "col-md-8"]}
                      properties={[
                        {
                          name: "telefoneCol",
                          label: "Phone",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Phone",
                          required: true,
                          onChange: this.inputChangeHandler
                        },
                        {
                          name: "emailCol",
                          label: "E-mail",
                          type: "email",
                          bsClass: "form-control",
                          placeholder: "E-mail",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <FormInputs
                      ncols={["col-md-12"]}
                      properties={[
                        {
                          name: "moradaCol",
                          label: "Adress",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Home Adress",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <ControlLabel>JOB</ControlLabel>
                    <div>
                      <select
                        name="idCargo"
                        className="form-control"
                        onChange={this.inputChangeHandler}
                      >
                        {this.state.data.map((element, key) => {
                          if (element["idCargo"] === 1) {
                            return null;
                          } else
                            return (
                              <option key={key} value={element["idCargo"]}>
                                {element["nomeCargo"]}
                              </option>
                            );
                        })}
                      </select>
                    </div>
                    <br></br>
                    <Button
                      bsStyle="info"
                      pullRight
                      fill
                      onClick={() => this.handleSubmit(this.state.formFields)}
                    >
                      Submit
                    </Button>
                    <div className="clearfix" />
                  </form>
                }
              />
            </Col>
          </Row>
        </Grid>
      </div>
    );
  }
}

export default withRouter(EmployeeRegister);

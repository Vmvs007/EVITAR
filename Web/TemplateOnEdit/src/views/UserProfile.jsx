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

import { FormInputs } from "components/FormInputs/FormInputs.jsx";
import Button from "components/CustomButton/CustomButton.jsx";
class EmployeeDetails extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      colaboradorInfo: {},
      password: {
        pass1: "",
        pass2: ""
      },
      isLoading1: false
    };
  }
  inputChangeHandler = e => {
    let password = { ...this.state.password };
    password[e.target.name] = e.target.value;
    this.setState({
      password
    });
  };
  handleSubmit = data => {
    if(data.pass1===data.pass2){
    this.Auth.fetch("https://evitarv2.azurewebsites.net/users/"+this.Auth.getIdUser(), {
      method: "PUT",
      body: JSON.stringify(
        {
          password:data.pass1
        }
      )
    })
      .then(res => {
        alert("Updated ");
        window.location.reload();
      })
      .catch(error => alert("Error! 1 " + error));
    }else{
      alert("Passwords don't match ");
    }
  };
  componentDidMount() {
    this.setState({ isLoading1: true });
    this.Auth.fetch(
      "https://evitarv2.azurewebsites.net/api/colaborador/" +
        this.Auth.getIdColaborador(),
      {
        method: "GET"
      }
    )
      .then(result => {
        this.setState({
          colaboradorInfo: result,
          isLoading1: false
        });
        console.log(result);
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
            <Col md={8}>
              <Card
                content={
                  <Col md={12}>
                <Card
                  content={
                  <div>
                    <h3>{this.state.colaboradorInfo.nomeColaborador}</h3>
                    <Col md={4}>
                      <p>Id:</p>
                      <p>CC:</p>
                      <p>NIF:</p>
                      <p>Birth Date:</p>
                      <p>Register Date:</p>
                      <p>Gender:</p>
                      <p>Email:</p>
                      <p>Phone:</p>
                      <p>Adress:</p>
                    </Col>
                    <Col md={8}>
                      <p>{this.state.colaboradorInfo.idColaborador}</p>
                      <p>{this.state.colaboradorInfo.ccColaborador}</p>
                      <p>{this.state.colaboradorInfo.nifColaborador}</p>
                      <p>
                        {Moment(this.state.colaboradorInfo.dataNasc).format(
                          "DD-MM-YYYY"
                        )}
                      </p>
                      <p>
                        {Moment(
                          this.state.colaboradorInfo.dataRegistoCol
                        ).format("DD-MM-YYYY")}
                      </p>
                      <p>{this.state.colaboradorInfo.generoCol}</p>
                      <p>{this.state.colaboradorInfo.emailCol}</p>
                      <p>{this.state.colaboradorInfo.telefoneCol}</p>
                      <p>{this.state.colaboradorInfo.moradaCol}</p>
                    </Col>
                  </div>}
                  />
                  </Col>
                }
              />
            </Col>
            <Col md={4}>
              <Card
                content={
                    <Col md={12}>
                      <Card
                        title="Change Password"
                        content={
                          <form>
                            <FormInputs
                              ncols={["col-md-12"]}
                              properties={[
                                {
                                  name: "pass1",
                                  label: "Password",
                                  type: "password",
                                  bsClass: "form-control",
                                  placeholder: "Password",
                                  required: true,
                                  onChange: this.inputChangeHandler
                                }
                              ]}
                            />
                            <FormInputs
                              ncols={["col-md-12"]}
                              properties={[
                                
                                {
                                  name: "pass2",
                                  label: "Confirm Password",
                                  type: "password",
                                  bsClass: "form-control",
                                  placeholder: "Confirm Password",
                                  required: true,
                                  onChange: this.inputChangeHandler
                                }
                              ]}
                            />
                            <Button
                              bsStyle="info"
                              pullRight
                              fill
                              onClick={() =>
                                this.handleSubmit(this.state.password)
                              }
                            >
                              Submit
                            </Button>
                            <div className="clearfix" />
                          </form>
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

export default EmployeeDetails;

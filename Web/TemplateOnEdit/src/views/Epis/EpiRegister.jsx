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
//import moment from "moment";
import { withRouter } from 'react-router';
import AuthService from "components/Authentication/AuthService.js";
//import axios from 'axios';
class EpiRegister extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      data: [],
      isLoading: false,
      formFields: {
        nomeEPI: "",
        dataRegistoEPI: new Date(),
        dataValidadeEPI: new Date(),
        idColaborador: parseInt(this.Auth.getIdUser()),
        idTipoEPI: 1
      }
    };
  }
  componentDidMount() {
    this.setState({ isLoading: true });

    this.Auth.fetch("https://evitar.azurewebsites.net/api/tipoepi", {
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
    if (e.target.name === "idTipoEPI")
      formFields[e.target.name] = parseInt(e.target.value);
    else formFields[e.target.name] = e.target.value;
    this.setState({
      formFields
    });
  };
  handleChange = date => {
    let formFields = { ...this.state.formFields };
    formFields["dataValidadeEPI"] = date;

    this.setState({
      formFields
    });
  };
  handleSubmit = data => {
    console.log(JSON.stringify(data));
    this.Auth.fetch("https://evitar.azurewebsites.net/api/EPI", {
      method: "POST",
      body: JSON.stringify(data)
    })
      .then(res => {
        alert("Adicionado ");
        this.props.history.push("/admin/epis/");
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
                title="Register EPI"
                content={
                  <form>
                    <FormInputs
                      ncols={["col-md-12"]}
                      properties={[
                        {
                          name: "nomeEPI",
                          label: "EPI Name",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "EPI Name",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <ControlLabel htmlFor="dataValidadeEPI">
                      Expiration Date
                    </ControlLabel>
                    <DatePicker
                      name="dataValidadeEPI"
                      id="dataValidadeEPI"
                      dateFormat="yyyy/MM/dd"
                      selected={this.state.formFields.dataValidadeEPI}
                      onChange={this.handleChange}
                      minDate={new Date()}
                      className={"form-control"}
                    />
                    <br></br>
                    <ControlLabel>EPI Type</ControlLabel>
                    <div>
                      <select
                        name="idTipoEPI"
                        className="form-control"
                        onChange={this.inputChangeHandler}
                      >
                        {this.state.data.map((element, key) => {
                          return (
                            <option key={key} value={element["idTipoEPI"]}>
                              {element["nomeTipoEPI"]}
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

export default withRouter(EpiRegister);

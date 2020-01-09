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
//import moment from "moment";
import { withRouter } from "react-router";
import MultiSelect from "@khanacademy/react-multi-select";
import AuthService from "components/Authentication/AuthService.js";
//import axios from 'axios';
class JobRegister extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      data: [],
      isLoading: false,
      formFields: {
        nomeCargo: "",
        zonaCargo: ""
      },
      selected: []
    };
  }
  componentDidMount() {
    this.setState({ isLoading: true });

    this.Auth.fetch("https://evitarv2.azurewebsites.net/api/tipoepi", {
      method: "GET"
    })
      .then(result => {
        let ola = [];
        result.map((element, key) => {
          ola[key] = {
            label: element["nomeTipoEPI"],
            value: parseInt(element["idTipoEPI"])
          };
          return null;
        });
        this.setState({
          data: ola,
          isLoading: false
        });
      })
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
  handleSubmit = data => {
    this.Auth.fetch("https://evitarv2.azurewebsites.net/api/cargo", {
      method: "POST",
      body: JSON.stringify(data)
    })
      .then(res => {
        this.state.selected.map(element => {
          this.Auth.fetch("https://evitarv2.azurewebsites.net/api/epicargo", {
            method: "POST",
            body: JSON.stringify({
              idCargo:parseInt(res["idCargo"]),
              idTipoEPI:parseInt(element)
            })
          })
            .then(res => {
              
            })
            .catch(error => alert("Error! 1 " + error));
            return null;
        });
        alert("Adicionado ");
        this.props.history.push("/admin/jobs/");
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
                title="Register Job"
                content={
                  <form>
                    <FormInputs
                      ncols={["col-md-12"]}
                      properties={[
                        {
                          name: "nomeCargo",
                          label: "Job Name",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Job Name",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <FormInputs
                      ncols={["col-md-12"]}
                      properties={[
                        {
                          name: "zonaCargo",
                          label: "Job Zone",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Job Zone",
                          required: true,
                          onChange: this.inputChangeHandler
                        }
                      ]}
                    />
                    <br></br>
                    <ControlLabel>EPI Type</ControlLabel>
                    <div>
                      <MultiSelect
                        options={this.state.data}
                        selected={this.state.selected}
                        onSelectedChanged={selected =>
                          this.setState({ selected })
                        }
                      />
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

export default withRouter(JobRegister);

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
import Button from "components/CustomButton/CustomButton.jsx";
//import moment from "moment";
import DatePicker from "react-datepicker";
import { FormInputs } from "components/FormInputs/FormInputs.jsx";
import { withRouter } from "react-router";
import Moment from "moment";
import AuthService from "components/Authentication/AuthService.js";
//import axios from 'axios';
class JobEditor extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      data: {},
      isLoading1: false,
      inicial: []
    };
  }
  componentDidMount() {
    let id = this.props.match.params.id;

    this.setState({ isLoading1: true });
    this.Auth.fetch("https://evitarv2.azurewebsites.net/api/epi/" + id, {
      method: "GET"
    })
      .then(result => {
        this.setState({
          data: result,
          isLoading1: false
        });
      })
      .catch(error => alert("Error! " + error.message));
  }
  inputChangeHandler = e => {
    let data = { ...this.state.data };
    if (e.target.name === "valido") {
      e.target.checked === true
        ? (data[e.target.name] = 1)
        : (data[e.target.name] = 0);
    } else data[e.target.name] = e.target.value;
    this.setState({
      data
    });
  };
  handleChange = date => {
    let data = { ...this.state.data };
    data["dataValidadeEPI"] = date;

    this.setState({
      data
    });
  };
  handleSubmit = data => {
    let id = this.props.match.params.id;
    
      this.Auth.fetch("https://evitarv2.azurewebsites.net/api/epi/" + id, {
        method: "PUT",
        body: JSON.stringify(
          this.state.data
        )
      })
        .then(res => {
          this.props.history.push("/admin/epis/" + id);
        })
        .catch(error => console.log(error));

      return null;
  };
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
                          value: this.state.data.nomeEPI,
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
                      selected={Moment(
                        this.state.data.dataValidadeEPI
                      ).toDate()}
                      onChange={this.handleChange}
                      minDate={new Date()}
                      className={"form-control"}
                    />
                    <br></br>
                    <ControlLabel htmlFor="valido">Valid</ControlLabel>
                    <input
                      type="checkbox"
                      id="valido"
                      name="valido"
                      className="form-check-input"
                      checked={this.state.data.valido}
                      onChange={this.inputChangeHandler}
                    />
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

export default withRouter(JobEditor);

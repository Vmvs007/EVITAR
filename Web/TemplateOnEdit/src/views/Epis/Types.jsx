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
//import { Link } from "react-router-dom";
//import { epis } from "variables/Variables.jsx";
import Popup from "reactjs-popup";
import Button from "components/CustomButton/CustomButton.jsx";
import { FormInputs } from "components/FormInputs/FormInputs.jsx";
import Moment from "moment";
import AuthService from "../../components/Authentication/AuthService.js";
import Card from "components/Card/Card.jsx";
class Types extends Component {
  constructor(props) {
    super(props);
    this.state = {
      thArray: ["Name"],
      data: [],
      isLoading: false,
      nomeTipoEPI: ""
    };
  }
  inputChangeHandler = e => {
    this.setState({
      nomeTipoEPI: e.target.value
    });
  };
  handleSubmit = data => {
    
    const Auth = new AuthService();
    Auth.fetch("https://evitarv2.azurewebsites.net/api/tipoepi", {
      method: "POST",
      body: JSON.stringify({"nomeTipoEPI":data})
    })
      .then(res => {
        alert("Adicionado ");
        this.setState({ isLoading: true });
        Auth.fetch("https://evitarv2.azurewebsites.net/api/tipoepi", {
          method: "GET"
        })
          .then(result =>
            this.setState({
              data: result,
              isLoading: false
            })
          )
          .catch(error => alert("Error! " + error.message));
      })
      .catch(error => alert("Error! 1 " + error));
  };
  componentDidMount() {
    Moment.locale("en");
    this.setState({ isLoading: true });
    const Auth = new AuthService();
    Auth.fetch("https://evitarv2.azurewebsites.net/api/tipoepi", {
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
  render() {
    if (this.state.isLoading) {
      return (
        <div className="content">
          <i className="fa fa-spinner  fa-3x fa-spin "></i>
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
                title="EPI Types Management"
                ctTableFullWidth
                ctTableResponsive
                content={
                  <div className="tabela">
                    <Popup
                      trigger={<button className="button right"> Add </button>}
                      modal
                      closeOnDocumentClick
                    >
                      <form>
                        <FormInputs
                          ncols={["col-md-12"]}
                          properties={[
                            {
                              name: "nomeEPI",
                              label: "EPI Type",
                              type: "text",
                              bsClass: "form-control",
                              placeholder: "EPI Type",
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
                            this.handleSubmit(this.state.nomeTipoEPI)
                          }
                        >
                          Submit
                        </Button>
                      </form>
                    </Popup>
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
                              <td>{prop["nomeTipoEPI"]}</td>
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

export default Types;

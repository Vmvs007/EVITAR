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
import { Link } from "react-router-dom";
//import { epis } from "variables/Variables.jsx";
import Moment from "moment";
import AuthService from "../../components/Authentication/AuthService.js";
import Card from "components/Card/Card.jsx";
class EpiManagement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      thArray: [
        "Name",
        "ID",
        "Date Register",
        "Date Valid",
        "Valid",
        "Inspector",
        "Details"
      ],
      data: [],
      isLoading: false
    };
  }
  componentDidMount() {
    Moment.locale("en");
    this.setState({ isLoading: true });
    const Auth = new AuthService();
    Auth.fetch("https://evitarv2.azurewebsites.net/api/EPI", {
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
          <i class="fa fa-spinner  fa-3x fa-spin "></i>
          <p>isLoading...</p>
        </div>
      );
    }
    return (
      <div className="content">
        <Grid fluid>
          <Row>
            <Col md={12}>
            <div className="right">
            <Link to={"epis/types"}>Epi Types   </Link>|
            <Link to={"epis/register"}><i class="fa fa-plus "></i> Add</Link>
            
            
            </div>
              <Card
                title="EPI Management"
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
                              <td>{prop["nomeEPI"]}</td>
                              <td>{prop["idEPI"]}</td>
                              <td>
                                {Moment(prop["dataRegistoEPI"]).format(
                                  "DD-MM-YYYY"
                                )}
                              </td>
                              <td>
                                {Moment(prop["dataValidadeEPI"]).format(
                                  "DD-MM-YYYY"
                                )}
                              </td>
                              <td>{prop["valido"]===0?("NOT"):("YES")}</td>
                              <td>{prop["idColaborador"]}</td>
                              <td>
                                <Link to={"/admin/epis/" + prop["idEPI"]}>
                                  Detalhes
                                </Link>
                              </td>
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

export default EpiManagement;

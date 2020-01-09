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
import { withRouter } from "react-router";
import MultiSelect from "@khanacademy/react-multi-select";
import AuthService from "components/Authentication/AuthService.js";
//import axios from 'axios';
class JobEditor extends Component {
  Auth = new AuthService();
  constructor(props) {
    super(props);

    this.state = {
      cargoInfo: {},
      cargoEpis: [],
      data: [],
      isLoading1: false,
      isLoading2: false,
      isLoading3: false,
      isLoading4: false,
      inicial: [],
      selected: [],
      toDelete: [],
      toInsert: []
    };
  }
  componentDidMount() {
    let id = this.props.match.params.id;

    this.setState({ isLoading1: true, isLoading2: true, isLoading3: true });
    this.Auth.fetch("https://evitarv2.azurewebsites.net/api/cargo/" + id, {
      method: "GET"
    })
      .then(result => {
        this.setState({
          cargoInfo: result,
          isLoading1: false
        });
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
          ola[key] = parseInt(element["idTipoEpi"]);

          return null;
        });
        this.setState({
          inicial: ola,
          selected: ola,
          isLoading2: false
        });
      })
      .catch(error => alert("Error! 1 " + error));

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
          isLoading3: false
        });
      })
      .catch(error => alert("Error! " + error.message));
  }

  handleSubmit = data => {
    let id = this.props.match.params.id;
    this.treat(id);
    /*
    this.state.selected.map(element => {
      this.Auth.fetch("https://evitarv2.azurewebsites.net/api/epicargo", {
        method: "POST",
        body: JSON.stringify({
          idCargo: parseInt(id),
          idTipoEPI: parseInt(element)
        })
      })
        .then(res => {})
        .catch(error => console.log("não inserido"));

      return null;
    });*/
    this.setState({ isLoading1: false });
  };
  treat(id) {
    const sleep = (milliseconds) => {
      return new Promise(resolve => setTimeout(resolve, milliseconds))
    }
    let inicial = this.state.inicial;
    let final = this.state.selected;
    let insertE = [];
    let deleteE = [];
    var countIn = 0;
    var countDe = 0;
    var esta;
    for (let h = 0; h < inicial.length; h++) {
      esta = false;
      for (let e = 0; e < final.length; e++) {
        if (inicial[h] === final[e]) {
          esta = true;
        }
      }
      if (esta === false) {
        deleteE[countDe] = inicial[h];
        countDe++;
      }
    }
    for (let i = 0; i < final.length; i++) {
      
      esta = false;
      for (let j = 0; j < inicial.length; j++) {
        if (inicial[j] === final[i]) {
          esta = true;
        }
      }
      if (esta === false) {
        insertE[countIn] = final[i];
        countIn++;
      }
    }
    Promise.all(
    insertE.map(element => {
      console.log(element+"pato")
      this.Auth.fetch("https://evitarv2.azurewebsites.net/api/epicargo", {
        method: "POST",
        body: JSON.stringify({
          idCargo: parseInt(id),
          idTipoEPI: parseInt(element)
        })
      })
        .then(res => {
        })
        .catch(error => console.log());

      return null;
    },
    deleteE.map(element => {
      console.log(element+"Arroz")
      this.Auth.fetch(
        "https://evitarv2.azurewebsites.net/api/epicargo/" + id + "/" + element,
        {
          method: "delete"
        }
      )
        .then(res => {
        })
        .catch(error => console.log());

      return null;
    })
    )
    ).then(sleep(500).then(() => {
      this.props.history.push("/admin/jobs/"+id)
    })
    )
  }
  
  
  render() {
    if (
      this.state.isLoading1 ||
      this.state.isLoading2 ||
      this.state.isLoading3
    ) {
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
                title="Editor Job"
                content={
                  <div>
                    <div>
                      <p>
                        <bold>Name:</bold> {this.state.cargoInfo["nomeCargo"]}
                      </p>
                      <p>
                        <bold>Zone:</bold> {this.state.cargoInfo["zonaCargo"]}
                      </p>
                    </div>
                    <form>
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
                        onClick={() => {
                          this.handleSubmit(this.state.formFields);
                        }}
                      >
                        Submit
                      </Button>
                      <div className="clearfix" />
                    </form>
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

export default withRouter(JobEditor);

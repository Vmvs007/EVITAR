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
import { thArray, tdArray } from "variables/Variables.jsx";

class Dashboard extends Component {
  createLegend(json) {
    var legend = [];
    for (var i = 0; i < json["names"].length; i++) {
      var type = "fa fa-circle text-" + json["types"][i];
      legend.push(<i className={type} key={i} />);
      legend.push(" ");
      legend.push(json["names"][i]);
    }
    return legend;
  }
  render() {
    return (
      <div className="content" >
        <Grid fluid>
          <Row>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-id text-warning" />}
                statsText="Employee Attendance"
                statsValue="5.252"
                statsIcon={<i className="fa fa-clock-o" />}
                statsIconText="Day"
              />
            </Col>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-id text-success" />}
                statsText="Employee Attendance"
                statsValue="41.795"
                statsIcon={<i className="fa fa-calendar-o" />}
                statsIconText="Week"
              />
            </Col>
            <Col lg={4} sm={4}>
              <StatsCard
                bigIcon={<i className="pe-7s-global text-danger" />}
                statsText="EPI Warnings"
                statsValue="420"
                statsIcon={<i className="fa fa-clock-o" />}
                statsIconText="Day"
              />
            </Col>

          </Row>

          <Row>
            <Col md={12}>
              <Card

                title="Flow View"
                ctTableFullWidth
                ctTableResponsive
                content={

                  <div className="tabela">
                    <Table hover  >

                      <thead>
                        <tr>
                          {thArray.map((prop, key) => {
                            return <th key={key}>{prop}</th>;
                          })}
                        </tr>
                      </thead>
                      <tbody>
                        {tdArray.map((prop, key) => {
                          return (
                            <tr key={key} id={key}>
                              {prop.map((props, keys) => {
                                if (keys !== 6) {
                                  if (props === 'Alert') {
                                    return <td title={prop[keys + 1].map((propss) => {
                                      return propss + "\n"
                                    })
                                    } style={{ backgroundColor: "red" }} key={keys} id={keys}>{props} </td>;
                                  }
                                  else if (props === 'Check') {
                                    return <td style={{ backgroundColor: "green" }} key={keys} id={keys}>
                                      {props}</td>;
                                  } else {
                                    return <td key={keys} id={keys}>
                                      {props}</td>;
                                  }

                                } else {
                                  return null;
                                }
                              })}
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

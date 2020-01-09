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
import Dashboard from "views/Dashboard.jsx";
import UserProfile from "views/UserProfile.jsx";
//import TableList from "views/TableList.jsx";
//import Typography from "views/Typography.jsx";
//import Icons from "views/Icons.jsx";
//import Maps from "views/Maps.jsx";
//import Notifications from "views/Notifications.jsx";
import JobManagement from "views/Jobs/JobManagement.jsx";
import JobDetails from "views/Jobs/JobDetails.jsx";
import JobRegister from "views/Jobs/JobRegister.jsx";
import JobEditor from "views/Jobs/JobEditor.jsx";
import EPIManagement from "views/Epis/EpiManagement.jsx";
import EpiDetails from "views/Epis/EpiDetails.jsx";
import EpiRegister from "views/Epis/EpiRegister.jsx";
import EpiTypes from "views/Epis/Types.jsx";
import EpiEditor from "views/Epis/EpiEditor.jsx";
import EmployeeManagement from "views/Employee/EmployeeManagement.jsx";
import EmployeeDetails from "views/Employee/EmployeeDetails.jsx";
import EmployeeRegister from "views/Employee/EmployeeRegister.jsx";
import Archive from "views/Archive.jsx";


const dashboardRoutes = [

  {
    path: "/dashboard",
    name: "Dashboard",
    icon: "pe-7s-graph",
    component: Dashboard,
    layout: "/admin",
    users: ["1", "2", "4"]
  },
  {
    path: "/archive",
    name: "Archive",
    icon: "pe-7s-date",
    component: Archive,
    layout: "/admin",
    users: ["1", "2", "4"]
  }, {
    path: "/jobs/register",
    name: "Job Register",
    icon: "pe-7s-portfolio",
    component: JobRegister,
    layout: "/admin",
    users: ["1", "2"],
    invisible: true
  }, 
  {
    path: "/jobs/editor/:id",
    name: "Job Editor",
    component: JobEditor,
    layout: "/admin",
    users: ["1", "2"],
    invisible: true
  },
  {
    path: "/jobs/:id",
    name: "Job Details",
    component: JobDetails,
    layout: "/admin",
    users: ["1", "2"],
    invisible: true
  },
  {
    path: "/jobs",
    name: "Job Management",
    icon: "pe-7s-portfolio",
    component: JobManagement,
    layout: "/admin",
    users: ["1", "2"]
  },

  {
    path: "/employees/register",
    name: "Employee Register",
    icon: "pe-7s-id",
    component: EmployeeRegister,
    layout: "/admin",
    users: ["1", "2"],
    invisible: true
  },
  {
    path: "/employees/:id",
    name: "Employee Details",
    component: EmployeeDetails,
    layout: "/admin",
    users: ["1", "2"],
    invisible: true
  },
  {
    path: "/employees",
    name: "Employee Management",
    icon: "pe-7s-id",
    component: EmployeeManagement,
    layout: "/admin",
    users: ["1", "2"]
  },
  {
    path: "/epis/types",
    name: "EPI Types",
    icon: "pe-7s-global",
    component: EpiTypes,
    layout: "/admin",
    users: ["1", "3"],
    invisible: true
  },
  {
    path: "/epis/register",
    name: "EPI Register",
    icon: "pe-7s-global",
    component: EpiRegister,
    layout: "/admin",
    users: ["1", "3"],
    invisible: true
  },{
    path: "/epis/editor/:id",
    name: "EPI Edit",
    component: EpiEditor,
    layout: "/admin",
    users: ["1", "3"],
    invisible: true
  },
  {
    path: "/epis/:id",
    name: "EPI Details",
    component: EpiDetails,
    layout: "/admin",
    users: ["1", "3"],
    invisible: true
  },

  {
    path: "/epis",
    name: "EPI Management",
    icon: "pe-7s-global",
    component: EPIManagement,
    layout: "/admin",
    users: ["1", "3"]
  },

  {
    path: "/user",
    name: "User Profile",
    icon: "pe-7s-user",
    component: UserProfile,
    layout: "/admin",
    invisible: true,
    users: ["1", "2", "3", "4"]
  }
  /*{
    path:"/",
    name:"Arquive",
    icon:"pe-7s-date",
    component: Arquive,
    layout: "/admin"
  },
  
  ,
  {
    path: "/table",
    name: "Table List",
    icon: "pe-7s-note2",
    component: TableList,
    layout: "/admin"
  },
  {
    path: "/typography",
    name: "Typography",
    icon: "pe-7s-news-paper",
    component: Typography,
    layout: "/admin"
  },
  {
    path: "/icons",
    name: "Icons",
    icon: "pe-7s-science",
    component: Icons,
    layout: "/admin"
  },
  {
    path: "/maps",
    name: "Maps",
    icon: "pe-7s-map-marker",
    component: Maps,
    layout: "/admin"
  },
  {
    path: "/notifications",
    name: "Notifications",
    icon: "pe-7s-bell",
    component: Notifications,
    layout: "/admin"
  },*/,
  {
    path: "",
    name: "Dashboard",
    icon: "pe-7s-graph",
    component: Dashboard,
    layout: "/admin",
    users: ["1", "2", "4"],
    invisible:true
  }

];

export default dashboardRoutes;


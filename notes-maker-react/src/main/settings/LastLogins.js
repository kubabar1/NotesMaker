import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button,ProgressBar} from "react-bootstrap";
import {LAST_LOGINS} from "../../environment";
import {withRouter} from "react-router-dom";
import { withCookies } from 'react-cookie';

class LastLogins extends Component {

    constructor(props, context) {
      super(props, context);
      const {cookies} = props;

      this.state = {
        csrfToken:cookies.get('XSRF-TOKEN'),
        lastLoginList:null
      };
    }

    componentDidMount(){
      fetch(LAST_LOGINS,{
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include'})
      .then(r=>r.json())
      .then(json=>this.setState({
          lastLoginList:json
      }))
      .catch(e=>console.log(e))
    }

    renderList = (item, id) => {
      return(<li key={id}>{item}</li>);
    }

    render() {
      const lastLoginList = this.state.lastLoginList;

      return (
        <div className="m-5">
          <h3>IP addresses of succeed last 10 logins to your account:</h3>
          <ul style={{listStyleType:"none"}}>
            {lastLoginList ? lastLoginList.map(this.renderList) : ""}
          </ul>
        </div>
      );
    }
}

export default withCookies(withRouter(LastLogins));

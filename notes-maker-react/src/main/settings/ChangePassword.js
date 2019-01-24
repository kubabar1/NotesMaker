import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button,ProgressBar} from "react-bootstrap";
import {UPDATE_USER_PASSWORD_ENDPOINT} from "../../environment";
import {withRouter} from "react-router-dom";
import { withCookies } from 'react-cookie';

class ChangePassword extends Component {

    constructor(props, context) {
      super(props, context);
      const {cookies} = props;

      this.handleChange = this.handleChange.bind(this);

      this.state = {
        currentPassword: '',
        passwordNew: '',
        passwordNewRepeat: '',
        errorMessage: null,
        passwordEntropy:0,
        passwordStrength:'',
        csrfToken:cookies.get('XSRF-TOKEN')
      };
    }


    calculateKeyEntropy = (key) => {
      const lower = key.search(/[a-z]/g)==-1 ? 0 : 26;
      const upper = key.search(/[A-Z]/g)==-1 ? 0 : 26;
      const digit = key.search(/[0-9]/g)==-1 ? 0 : 10;
      const special = key.search(/[^\w]/g)==-1 ? 0 : 33;

      const sum = lower+upper+digit+special;

      const keyLength = key.length;
      const entropy = Math.log2(sum)*keyLength;

      return entropy;
    }



    rateKeyStrength = (key) => {
    	const e = this.calculateKeyEntropy(key);

    	if( e>=0 && e<28){
        this.setState({passwordEntropy:e,passwordStrength:'Very weak'});
    	}else if (e>=28 && e<35){
        this.setState({passwordEntropy:e,passwordStrength:'Weak'});
    	}else if (e>=35 && e<59 ){
        this.setState({passwordEntropy:e,passwordStrength:'Moderate'});
    	}else if (e>=59  && e<127 ){
        this.setState({passwordEntropy:e,passwordStrength:'Strong'});
    	}else if (e>=128){
        this.setState({passwordEntropy:e,passwordStrength:'Very strong'});
      }
    }




    handleChange = (e) => {
      const id = e.target.id;
      this.setState({ [id]: e.target.value });
    }

    onSubmit = (e) => {
      e.preventDefault();

      const currentPassword = this.state.currentPassword;
      const passwordNew = this.state.passwordNew;
      const passwordNewRepeat = this.state.passwordNewRepeat;

      if(passwordNew.length<8){
        this.setState({ errorMessage: "New password cannot be shorter than 8 chars" });
      }else{
        if(passwordNew==passwordNewRepeat){
          const formData = new FormData();

          formData.append("currentPassword", currentPassword);
          formData.append("newPassword", passwordNew);

          fetch(UPDATE_USER_PASSWORD_ENDPOINT, {
            credentials: 'include',
            method: 'PUT',
            body: formData,
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken
            },
          })
          .then(r => {
            r.ok ? this.props.history.push('/') : r.text().then((text) => {this.setState({ errorMessage: text })});
          }).catch(e => console.log(e));

        }else{
          this.setState({ errorMessage: "New password and new password repeat are different" });
        }
      }
    }


    render() {
        const errorMessage = this.state.errorMessage;
        const passwordEntropy = this.state.passwordEntropy;
        const passwordStrength = this.state.passwordStrength;
        const strengthPercent = (passwordEntropy*100)/128;

        return (
          <div className="m-5">
            <form onSubmit={this.onSubmit}>
              <FormGroup controlId="currentPassword">
                <ControlLabel>Current password</ControlLabel>
                <FormControl
                  type="password"
                  value={this.state.currentPassword}
                  placeholder="Enter current password"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="passwordNew">
                <ControlLabel>New password</ControlLabel>
                <FormControl
                  type="password"
                  value={this.state.passwordNew}
                  placeholder="Enter password"
                  onChange={(e) => {this.handleChange(e); this.rateKeyStrength(e.target.value);}}
                />
              </FormGroup>

              <ProgressBar now={strengthPercent}/>
              <p>{passwordStrength}</p>

              <FormGroup controlId="passwordNewRepeat">
                <ControlLabel>Repeat password</ControlLabel>
                <FormControl
                  type="password"
                  value={this.state.passwordNewRepeat}
                  placeholder="Enter password"
                  onChange={this.handleChange}
                />
              </FormGroup>

              {errorMessage ?
                <div className="alert alert-danger" role="alert" style={{marginTop:10, marginBottom:10}}>
                  {errorMessage}
                </div>
              : ""}


              <Button bsStyle="primary" type="submit">Update</Button>
            </form>


          </div>
        );
    }
}

export default withCookies(withRouter(ChangePassword));

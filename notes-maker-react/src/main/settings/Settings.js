import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button} from "react-bootstrap";
import {Link} from "react-router-dom";

class Settings extends Component {

    constructor(props, context) {
      super(props, context);

      this.handleChange = this.handleChange.bind(this);

      this.state = {
        name: '',
        surname: '',
        login: '',
        email: '',
        birthdate: ''
      };
    }


    handleChange = (e) => {
      const id = e.target.id;
      this.setState({ [id]: e.target.value });
    }

    render() {
        return (
          <div className="m-5">
            <form>
              <FormGroup controlId="name">
                <ControlLabel>Name</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.name}
                  placeholder="Enter name"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="surname">
                <ControlLabel>Surname</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.surname}
                  placeholder="Enter surname"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="login">
                <ControlLabel>Login</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.login}
                  placeholder="Enter login"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="email">
                <ControlLabel>E-mail</ControlLabel>
                <FormControl
                  type="email"
                  value={this.state.email}
                  placeholder="Enter email"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="birthdate">
                <ControlLabel>Birth date</ControlLabel>
                <FormControl
                  type="date"
                  value={this.state.birthdate}
                  placeholder="Enter birthdate"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <Button bsStyle="primary" type="submit">Update</Button>
            </form>
            <Link to={'/change-password'}>
              <Button bsStyle="link" style={{marginTop:20}}>Change password</Button>
            </Link>
          </div>
        );
    }
}

export default Settings;

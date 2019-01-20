import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button} from "react-bootstrap";

class Settings extends Component {

    constructor(props, context) {
      super(props, context);

      this.handleChange = this.handleChange.bind(this);

      this.state = {
        currentPassword: '',
        passwordNew: '',
        passwordNewRepeat: ''
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
              <FormGroup controlId="currentPassword">
                <ControlLabel>Current password</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.currentPassword}
                  placeholder="Enter current password"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="passwordNew">
                <ControlLabel>New password</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.passwordNew}
                  placeholder="Enter password"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="passwordNewRepeat">
                <ControlLabel>Repeat password</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.passwordNewRepeat}
                  placeholder="Enter password"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <Button bsStyle="primary" type="submit">Update</Button>
            </form>
          </div>
        );
    }
}

export default Settings;

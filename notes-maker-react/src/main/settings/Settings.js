import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {UPDATE_USER_DATA_ENDPOINT} from "../../environment";
import { withCookies } from 'react-cookie';

class Settings extends Component {

    constructor(props, context) {
      super(props, context);
      const {cookies} = props;

      this.state = {
        name: '',
        surname: '',
        login: '',
        email: '',
        birthdate: '',
        csrfToken:cookies.get('XSRF-TOKEN')
      };
    }

    componentDidMount(){
      const currentUser = this.props.currentUser;

      if(currentUser){
        this.setState({
          name: currentUser.name,
          surname: currentUser.surname,
          email: currentUser.email,
          birthdate: currentUser.birthdate
        });
      }
    }

    updateUser = (event) => {
      event.preventDefault();

      const formData = new FormData();

      const name = this.state.name;
      const surname = this.state.surname;
      const email = this.state.email;
      const birthdate = this.state.birthdate;

      formData.append("name", name);
      formData.append("surname", surname);
      formData.append("email", email);
      formData.append("birthDate", birthdate);

      fetch(UPDATE_USER_DATA_ENDPOINT, {
        credentials: 'include',
        method: 'PUT',
        body: formData,
        headers: {
            'X-XSRF-TOKEN': this.state.csrfToken
        },
      })
      .then(r => this.props.getCurrentUser())
      .catch(e => console.log(e));

    }


    handleChange = (e) => {
      const id = e.target.id;
      this.setState({ [id]: e.target.value });
    }

    render() {

        return (
          <div className="m-5">
            <form onSubmit={this.updateUser}>
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
            <br></br>
            <Link to={'/last-logins'}>
              <Button bsStyle="link" style={{marginTop:20}}>Last login list</Button>
            </Link>
          </div>
        );
    }
}

export default withCookies(Settings);

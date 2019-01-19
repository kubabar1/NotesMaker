import React, { Component } from 'react';
import { Modal } from 'react-bootstrap';
import { Redirect, withRouter } from 'react-router-dom';

class Login extends Component {

  constructor(props, context) {
    super(props, context);

    this.state = {
      redirectToReferrer: false,
      username:"",
      password:""
    };
  }

  componentWillReceiveProps(){
    this.setState({ redirectToReferrer: false });
  }

  login = () => {
    let username = this.state.username;
    let password = this.state.password;

    this.props.authenticate(() => {
      this.setState({
        username: "",
        password: ""
      });

      this.props.handleClose();
      this.setState({ redirectToReferrer: true });
    }, username, password);

  };

  handleInputChange = (event) => {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  submitHandler = (e) => {
      e.preventDefault();
      this.login();
  }

  render() {
    let { from } = this.props.location.state || { from: { pathname: "/" } };
    let redirectToReferrer = this.state.redirectToReferrer;

    if (redirectToReferrer){
      return <Redirect to={from} />;
    }

    return (
      <div>
        <Modal show={this.props.show} onHide={this.props.handleClose}>
          <Modal.Header closeButton>
            <h4><span className="glyphicon glyphicon-lock"></span> Login</h4>
          </Modal.Header>
          <Modal.Body>
            <form onSubmit={this.submitHandler}>
              <div className="form-group">
                <label><span className="glyphicon glyphicon-user"></span> Username</label>
                <input type="text" className="form-control" id="usrname" placeholder="Enter username" name="username" value={this.state.username} onChange={this.handleInputChange}></input>
              </div>
              <div className="form-group">
                <label><span className="glyphicon glyphicon-eye-open"></span> Password</label>
                <input type="password" className="form-control" id="psw" placeholder="Enter password"  name="password" value={this.state.password} onChange={this.handleInputChange}></input>
              </div>
              <button id="submit-button" type="submit" className="btn btn-block"><span className="glyphicon glyphicon-off"></span> Login</button>
            </form>
          </Modal.Body>
          <Modal.Footer>
            <button type="submit" className="btn btn-danger btn-default pull-left" data-dismiss="modal" onClick={this.props.handleClose}><span className="glyphicon glyphicon-remove"></span> Cancel</button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }
}

export default withRouter(Login);

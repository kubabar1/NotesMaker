import React, {Component} from 'react';
import {Button} from 'react-bootstrap';
import {SINGLE_USER_NOTE,PUBLISH_ENDPOINT,UNPUBLISH_ENDPOINT,DELETE_ENDPOINT} from "../../environment";
import {withRouter} from "react-router-dom";
import { withCookies } from 'react-cookie';

class NoteView extends Component {

    constructor(props){
      super(props);
      const {cookies} = props;

      this.state={
        note:null,
        unauthorizedError:false,
        published:null,
        csrfToken:cookies.get('XSRF-TOKEN')
      }
    }

    componentDidMount(){
      fetch(SINGLE_USER_NOTE+"/"+this.props.match.params.id,{
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include'})
      .then(resp => resp.ok ? resp.json() : this.setState({unauthorizedError:true}))
      .then(json=>this.setState({
          note:json,
          published:json.published
        })
      ).catch(e=>console.log(e));
    }


    publishButton = (e) => {
        const note = this.state.note;
        const published = this.state.published;
        const id = note.id;

        if(published){
          fetch(UNPUBLISH_ENDPOINT+"/"+id,{
            method:"PUT",
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken
            },
            credentials: 'include'})
          .then(resp => this.setState({published:false}))
          .catch(e=>console.log(e));
        }else{
          fetch(PUBLISH_ENDPOINT+"/"+id,{
            method:"PUT",
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken
            },
            credentials: 'include'})
          .then(resp => {this.setState({published:true});})
          .catch(e=>console.log(e));
        }
    }

    editButton = (e) => {
      console.log("edit")
    }

    deleteButton = (e) => {
      const id = this.state.note.id;

      fetch(DELETE_ENDPOINT+"/"+id,{
        method:"DELETE",
        headers: {
            'X-XSRF-TOKEN': this.state.csrfToken
        },
        credentials: 'include'})
      .then(resp => this.props.history.push('/my-notes'))
      .catch(e=>console.log(e));
    }

//<Button bsStyle="info" style={{marginLeft:20,marginRight:20}} onClick={this.editButton}><i className="fas fa-edit"></i> Edit</Button>
    render() {
        const handle  = this.props.match.params;
        const author = handle.author;
        const unauthorizedError = this.state.unauthorizedError;
        const noteId = handle.id;
        const currentUser = this.props.currentUser ? this.props.currentUser.login : null;

        const note = this.state.note;
        const published = this.state.published;

        return (
          <div className="" style={{marginTop:10}}>
          <div className="container-fluid text-center" style={{marginTop:20}}>
            {note && (currentUser == author) && !unauthorizedError ?
              <div>
                <Button bsStyle="primary" style={{marginLeft:20,marginRight:20}} onClick={this.publishButton}><i className="fas fa-share-alt"></i>{published ? "Unpublish" : "Publish"}</Button>

                <Button bsStyle="danger" style={{marginLeft:20,marginRight:20}} onClick={this.deleteButton}><i className="fas fa-trash-alt"></i> Delete</Button>
              </div>
              : ""
            }
          </div>
          <hr></hr>
            <div className="card-container col-md-10  col-md-offset-1" style={{marginTop:10}}>
              <div className="card" style={{padding:5}}>
                <div className="text-center">
                  <small>{note ? note.creationDate : ""} - {note ? note.user.login : ""}</small>
                </div>
                <h3 className="text-center">{note ? note.name : ""}</h3>
                <div className="m-4 text-center">
                  <p className="card-text">{note ? note.content : ""}</p>
                </div>
              </div>
              {unauthorizedError ?
                <div className="alert alert-danger" role="alert" style={{marginTop:30}}>
                  You are not authorized to see this page!
                </div>
              : ""}
            </div>
          </div>
        );
    }
}

export default withCookies(withRouter(NoteView));

import React, {Component} from 'react';
import {Button} from 'react-bootstrap';
import {SINGLE_USER_NOTE} from "../../environment";

class NoteView extends Component {

    constructor(props){
      super(props);

      this.state={
        note:null,
        unauthorizedError:false
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
          note:json
        })
      ).catch(e=>console.log(e));
    }


    render() {
        const handle  = this.props.match.params;
        const author = handle.author;
        const unauthorizedError = this.state.unauthorizedError;
        const noteId = handle.id;
        const currentUser = this.props.currentUser ? this.props.currentUser.login : null;

        const note = this.state.note;

        return (
          <div className="" style={{marginTop:10}}>
          <div className="container-fluid text-center" style={{marginTop:20}}>
            {(currentUser == author) && !unauthorizedError ?
              <div>
                <Button bsStyle="primary" style={{marginLeft:20,marginRight:20}}><i className="fas fa-share-alt"></i> Publish</Button>
                <Button bsStyle="info" style={{marginLeft:20,marginRight:20}}><i className="fas fa-edit"></i> Edit</Button>
                <Button bsStyle="danger" style={{marginLeft:20,marginRight:20}}><i className="fas fa-trash-alt"></i> Delete</Button>
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

export default NoteView;

import React, {Component} from 'react';
import Note from './Note';
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {ALL_PUBLISHED_NOTES_LIST} from "../../environment";

class PublicNotes extends Component {

    constructor(props){
      super(props);

      this.state={
        notesList:null
      }
    }

    componentDidMount(){
      fetch(ALL_PUBLISHED_NOTES_LIST,{
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include'})
      .then(resp => resp.json())
      .then(json=>this.setState({
        notesList:json
      }));
    }

    render() {
        const notesList = this.state.notesList;

        return (
          <div className="text-center">
            <div className="container-fluid text-center" style={{marginTop:10}}>
              <Link to={'/my-notes'} className="">
                <Button bsStyle="link">My notes</Button>
              </Link>
              <Link to={'/public-notes'} className="">
                <Button bsStyle="primary" style={{marginLeft:30}}>Public notes</Button>
              </Link>
            </div>
            <hr></hr>
            <div className="container-fluid m-4">
              {notesList ?
                notesList.map((element, it)=>{
                  let content = element.content.length > 150 ? element.content.substring(0,150)+" ..." : element.content;
                  return <Note key={it} id={element.id} content={content} title={element.name} date={element.creationDate} author={element.user.login}/>
              }) : ""}
            </div>
          </div>
        );
    }
}

export default PublicNotes;

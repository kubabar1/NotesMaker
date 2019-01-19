import React, {Component} from 'react';
import {Link} from "react-router-dom";

class Note extends Component {
    render() {
        const content = this.props.content;
        const title = this.props.title;
        const date = this.props.date;
        const id = this.props.id;
        const author = this.props.author;

        return (
          <Link to={'/notes/'+id} style={{color:"#000000"}}>
            <div className="card-container col-lg-3 col-md-4 m-5">
              <div className="card" style={{padding:5}}>
                <div className="text-center">
                  <small>{date+" - "+author}</small>
                </div>
                <h3 className="text-center">{title}</h3>
                <div className="m-4">
                  <p className="card-text">{content}</p>
                </div>
              </div>
            </div>
          </Link>
        );
    }
}

export default Note;

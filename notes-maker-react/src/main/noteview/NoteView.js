import React, {Component} from 'react';
import {Button} from 'react-bootstrap';

class NoteView extends Component {


    render() {
        const text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam elementum nisl in lacus maximus commodo. Pellentesque accumsan turpis massa, at vehicula mauris bibendum quis. Aenean luctus justo a eros vestibulum, vulputate dictum ex condimentum. Nunc sit amet diam porttitor, lobortis lectus vel, maximus sem. Nunc dapibus dapibus nisl vel mattis. Nam lacinia tellus vitae erat tincidunt, interdum aliquam lorem tempus. Aenean sed quam at dolor vehicula vestibulum in vel erat.   Donec mollis non justo vitae dignissim. Cras pharetra, nisl nec lacinia suscipit, elit dui dignissim dolor, a bibendum massa tellus eu magna.";
        const title = "Test";
        const date = "21-12-2018 12:10:45";
        const content = text.substring(0, 150)+" ...";
        const id=1;

        return (
          <div className="" style={{marginTop:10}}>
          <div className="container-fluid text-center" style={{marginTop:20}}>
            <Button bsStyle="primary" style={{marginLeft:20,marginRight:20}}><i className="fas fa-share-alt"></i> Publish</Button>
            <Button bsStyle="info" style={{marginLeft:20,marginRight:20}}><i className="fas fa-edit"></i> Edit</Button>
            <Button bsStyle="danger" style={{marginLeft:20,marginRight:20}}><i className="fas fa-trash-alt"></i> Delete</Button>
          </div>
          <hr></hr>
            <div className="card-container col-md-10  col-md-offset-1" style={{marginTop:10}}>
              <div className="card" style={{padding:5}}>
                <div className="text-center">
                  <small>{date}</small>
                </div>
                <h3 className="text-center">{title}</h3>
                <div className="m-4">
                  <p className="card-text">{content}</p>
                </div>
              </div>
            </div>
          </div>
        );
    }
}

export default NoteView;

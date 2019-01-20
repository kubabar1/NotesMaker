import React, {Component} from 'react';
import Note from './Note';
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";

class MyNotes extends Component {

    constructor(props){
      super(props);

      this.state={

      }
    }

    componentDidMount(){

    }

    render() {
        const text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam elementum nisl in lacus maximus commodo. Pellentesque accumsan turpis massa, at vehicula mauris bibendum quis. Aenean luctus justo a eros vestibulum, vulputate dictum ex condimentum. Nunc sit amet diam porttitor, lobortis lectus vel, maximus sem. Nunc dapibus dapibus nisl vel mattis. Nam lacinia tellus vitae erat tincidunt, interdum aliquam lorem tempus. Aenean sed quam at dolor vehicula vestibulum in vel erat. Donec mollis non justo vitae dignissim. Cras pharetra, nisl nec lacinia suscipit, elit dui dignissim dolor, a bibendum massa tellus eu magna.";
        const title = "Test";
        const date = "21-12-2018 12:10:45";
        const author = "Jan Kowalski";
        const content = text.substring(0, 150)+" ...";
        const id=1;

        return (
          <div className="text-center">
            <div className="container-fluid text-center" style={{marginTop:10}}>
              <Link to={'/my-notes'} className="">
                <Button bsStyle="primary">My notes</Button>
              </Link>
              <Link to={'/public-notes'} className="">
                <Button bsStyle="link" style={{marginLeft:30}}>Public notes</Button>
              </Link>
            </div>
            <hr></hr>
            <div className="container-fluid m-4">
              <Note id={id} content={content} title={title} date={date} author={author}/>
              <Note id={id} content={content} title={title} date={date} author={author}/>
              <Note id={id} content={content} title={title} date={date} author={author}/>
              <Note id={id} content={content} title={title} date={date} author={author}/>
            </div>
          </div>
        );
    }
}

export default MyNotes;

import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button} from "react-bootstrap";
import {ADD_NOTE_ENDPOINT} from "../../environment";
import { withCookies } from 'react-cookie';

class AddNote extends Component {

    constructor(props, context) {
      super(props, context);
      const {cookies} = props;

      this.state = {
        noteTitle: '',
        noteContent: '',
        errorMessage:null,
        csrfToken:cookies.get('XSRF-TOKEN')
      };
    }


    handleTitleChange = (e) => {
      const id = e.target.id;
      const value = e.target.value;
      this.setState({ [id]: value });
    }

    handleContentChange = (e) => {
      const id = e.target.id;
      const value = e.target.value;

      if(value.length>=255){
        this.setState({ errorMessage:"Text cannot be longer than 255 chars!" });
      }else{
        this.setState({ [id]: value, errorMessage:null });
      }
    }

    handleSubmit = (event) => {
      event.preventDefault();

      const formData = new FormData();

      const noteTitle = this.state.noteTitle;
      const noteContent = this.state.noteContent;

      formData.append("name", noteTitle);
      formData.append("content", noteContent);

      fetch(ADD_NOTE_ENDPOINT, {
        credentials: 'include',
        method: 'POST',
        body: formData,
        headers: {
            'X-XSRF-TOKEN': this.state.csrfToken
        },
      }).then(response => {
        this.setState({
          noteTitle: '',
          noteContent: '',
          errorMessage:null
        });
      }).catch(e => console.log(e));
    }

    render() {

      const errorMessage = this.state.errorMessage;

      return (
        <div className="m-5">
          <form onSubmit={this.handleSubmit}>
            <FormGroup controlId="noteTitle">
              <ControlLabel>Note title</ControlLabel>
              <FormControl
                type="text"
                value={this.state.noteTitle}
                placeholder="Enter note title"
                onChange={this.handleTitleChange}
              />
            </FormGroup>

            <FormGroup controlId="noteContent">
              <ControlLabel>Note content</ControlLabel>
              <FormControl
                componentClass="textarea"
                value={this.state.noteContent}
                placeholder="Enter note content"
                onChange={this.handleContentChange}
              />
            </FormGroup>

            {errorMessage ?
              <div className="alert alert-danger" role="alert">
                {errorMessage}
              </div>
            : ""}

            <Button bsStyle="primary" type="submit">Add</Button>
          </form>
        </div>
      );
    }
}

export default withCookies(AddNote);

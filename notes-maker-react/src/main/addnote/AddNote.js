import React, {Component} from 'react';
import {FormGroup,ControlLabel,FormControl,HelpBlock,Button} from "react-bootstrap";

class AddNote extends Component {

    constructor(props, context) {
      super(props, context);

      this.handleChange = this.handleChange.bind(this);

      this.state = {
        noteTitle: '',
        noteContent: ''
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
              <FormGroup controlId="noteTitle">
                <ControlLabel>Note title</ControlLabel>
                <FormControl
                  type="text"
                  value={this.state.noteTitle}
                  placeholder="Enter note title"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <FormGroup controlId="noteContent">
                <ControlLabel>Note content</ControlLabel>
                <FormControl 
                  componentClass="textarea"
                  value={this.state.noteContent}
                  placeholder="Enter note content"
                  onChange={this.handleChange}
                />
              </FormGroup>

              <Button bsStyle="primary" type="submit">Add</Button>
            </form>
          </div>
        );
    }
}

export default AddNote;

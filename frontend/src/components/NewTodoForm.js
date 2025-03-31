import React, {useState} from 'react';

// A new form that allows the user to input data in the browser, nd have it updated
function NewTodoForm(props) {

    const [description, setDescription] = useState('')
    const [assigned, setAssigned] = useState('');

    // const descriptionChange = (event) => {
    //     console.log('description', event.target.value)
    //     setDescription(event.target.value);
    // }

    const assignedChange = (event) => {
        console.log('assigned', event.target.value);
        setAssigned(event.target.value);
    }

    const submitTodo = () => {
        // Make sure both the description and assigned are not empty
        if (description !== '' && assigned !== '') {
            props.addTodo(description, assigned);
            setDescription('');
            setAssigned('');
        }
    }
    return (
        <div className='mt-5'>
            <form>
                <div className='mb-3'>
                    <label className='form-label'>Assigned</label>
                    <input 
                    type='text' 
                    className='form-control' 
                    required
                    onChange={assignedChange}
                    value={assigned}>

                    </input>
                </div>
                <div className='mb-3'>
                    <label className='form-label'>Description</label>
                    <textarea
                    className='form-control'
                    rows={3}
                    required
                    // On change, grabe the event, set the description using the arrow function; this is the alternative/more efficient approach of creating the function above & referencing the pointer to that variable.
                    onChange={e => setDescription(e.target.value)}
                    value={description}
                    >
                    </textarea>
                </div>
                <button 
                type='button' 
                className='btn btn-primary mt-3>'
                onClick={submitTodo}>
                    Add Todo
                    </button>
            </form>

        </div>
    )
}

export default NewTodoForm;
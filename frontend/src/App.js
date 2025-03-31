import React, {useState} from 'react';
// import logo from './logo.svg';
import './App.css';
import TodoTable from './components/TodoTable';
import NewTodoForm from './components/NewTodoForm';
function App() {

  const [showAddTodoForm, setShowAddTodoForm] = useState(false);

  const [todos, setTodos] = useState([
    {rowNumber: 1, rowDescription: 'Feed the bird', rowAssigned: 'User One'},
    {rowNumber: 2, rowDescription: 'Water the plants', rowAssigned: 'User Two'},
    {rowNumber: 3, rowDescription: 'Perfect Just Dance Routine', rowAssigned: 'User One'},
    {rowNumber: 4, rowDescription: 'Plan for my trip', rowAssigned: 'User One'}
  ]);

  const addTodo = (description, assigned) => {
    let rowNumber = 0;
    if (todos.length > 0) {
      // Find the last item in the list to identify the length, and add 1
      rowNumber = todos[todos.length-1].rowNumber + 1;
    } else {
      rowNumber = 1;
    }
      const newTodo = {
        rowNumber: rowNumber, 
        rowDescription: description, 
        rowAssigned: assigned}

      setTodos(todos => [...todos, newTodo])
      console.log(todos);
  }

  const deleteTodo = (deleteTodoRowNumber) => {
    let filtered = todos.filter(function (value) {
      return value.rowNumber !== deleteTodoRowNumber;
    });
    setTodos(filtered);
  }
  return (
    <div className='mt-5 container'>
      <div className='card'>
        <div className='card-header'>
          To-Dos
        </div>
        <div className='card-body'>
          <TodoTable todos={todos} deleteTodo={deleteTodo}/>
          <button className='btn btn-primary' onClick={() => setShowAddTodoForm(!showAddTodoForm)}>
            {showAddTodoForm ? 'Close New Todo' : 'New Todo'}
          </button>
          {showAddTodoForm &&
          // {/* We can pass in functions as a prop, in this case, we want to pass in the "addTodo" function */}
          <NewTodoForm addTodo={addTodo}/>
          }
        </div>
      </div>
    </div>
  );
}

export default App;

// This is how a component of REACT is created with dynamic data, and variables
function RowItemDynamic(props) {

    return (
        <tr onClick={() => props.deleteTodo(props.rowNumber)}>
            <th scope='row'>{props.rowNumber}</th>
            <td>{props.rowDescription}</td>
            <td>{props.rowAssigned}</td>
        </tr>
    )
}

// Allows us to usethis component within the application
export default RowItemDynamic
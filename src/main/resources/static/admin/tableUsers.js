async function getAllUsers() {
    let temporary = '';
    const tableAllUsers = document.querySelector('#tableUsers tbody');
    await myFetch.getAllUsers()
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                let roles = user.roles.map(role => " " + role.role);
                temporary += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    <td>
                        <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                             data-toggle="modal" data-target="#editModal">Edit</button>
                    </td>
                    <td>
                        <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger"
                             data-toggle="modal" data-target="#deleteModal">Delete</button>
                    </td>
                </tr>
               `;


                $("#tableUsers").on('click', '.btn', (event) => {
                    console.log("edit or delete button pressed");
                    let defaultModal = $('#defaultModal');

                    let targetButton = $(event.target);
                    let buttonUserId = targetButton.attr('data-userid');
                    let buttonAction = targetButton.attr('data-action');

                    defaultModal.attr('data-userid', buttonUserId);
                    defaultModal.attr('data-action', buttonAction);
                    defaultModal.modal('show');
                })

                tableAllUsers.innerHTML = temporary;
            })
        })
}
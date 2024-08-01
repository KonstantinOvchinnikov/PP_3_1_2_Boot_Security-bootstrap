async function formDeleteUser(modal, id) {
    let user = (await myFetch.getUserById(id)).json();

    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(deleteButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="deleteUser">
                <div class="form-group">
                    <label for="userId" class="col-form-label">ID</label>
                    <input type="text" class="form-control username" id="userId" value="${user.id}" readonly>
                </div>

                <div class="form-group">
                    <label for="username" class="col-form-label">Name</label>
                    <input type="text" class="form-control username" id="name" value="${user.name}" readonly>
                </div>

                <div class="form-group">
                    <label for="email" class="com-form-label">Surname</label>
                    <input type="text" class="form-control" id="surname" value="${user.surname}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="email" class="com-form-label">Age</label>
                    <input type="text" class="form-control" id="age" value="${user.age}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="email" class="com-form-label">Email</label>
                    <input type="text" class="form-control" id="email" value="${user.email}" readonly>
                </div>

                <div class="form-group">
                    <label for="roles" class="com-form-label">Role:</label>
                    <select id="roles" class="form-control select" size="2" name="roles" style="max-height: 100px" disabled>
                        <option>${user.roles.map(role => " " + role.role)}</option>
                    </select>
                </div>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#deleteButton").on('click', async () => {
        const response = await myFetch.deleteUser(id);

        if (response.ok) {
            await getAllUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}
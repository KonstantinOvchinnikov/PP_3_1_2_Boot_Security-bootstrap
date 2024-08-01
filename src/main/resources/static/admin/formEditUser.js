async function formEditUser(modal, id) {
    let user = (await myFetch.getUserById(id)).json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-info" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(editButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
               <div class="form-group">
                    <label for="userId" class="col-form-label">ID</label>
                    <input type="text" class="form-control" id="id" value="${id}" readonly>
               </div>

               <div class="form-group">
                    <label for="username" class="col-form-label">Name</label>
                    <input type="text" class="form-control" id="name" value="${user.name}" >
               </div>

               <div class="form-group">
                    <label for="email" class="com-form-label">Surname</label>
                    <input type="text" class="form-control" id="surname" value="${user.surname}" >
                </div>
                
                <div class="form-group">
                    <label for="email" class="com-form-label">Age</label>
                    <input type="text" class="form-control" id="age" value="${user.age}" >
                </div>
                
                <div class="form-group">
                    <label for="email" class="com-form-label">Email</label>
                    <input type="text" class="form-control" id="email" value="${user.email}" >
                </div>

                <div class="form-group">
                    <label for="password" class="com-form-label">Password</label>
                    <input type="password" class="form-control" id="password" value="" >
                </div>

                <div class="form-group">
                    <label for="roles" class="com-form-label">Role</label>
                    <select multiple id="roles" size="2" class="form-control form-control-sm">
                    <option value="1">USER</option>
                    <option value="2">ADMIN</option>
                    </select>
                </div>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let thisRoles = () => {
            let tempArray = []
            let options = document.querySelector('#roles').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    tempArray.push(listRoles[i])
                }
            }
            return tempArray;
        }

        let name = modal.find("#name").val().trim();
        let surname = modal.find("#surname").val().trim();
        let age = modal.find("#age").val().trim();
        let email = modal.find("#email").val().trim();
        let password = modal.find("#password").val().trim();
        let user = {
            id: id,
            name: name,
            surname: surname,
            age: age,
            email: email,
            password: password,
            roles: thisRoles()
        }
        const response =  await myFetch.saveUser(user);

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
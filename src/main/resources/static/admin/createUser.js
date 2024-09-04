async function getNewUserForm() {
    let button = $(`#addUser`);
    let form = $(`#addForm`);
    button.on('click', () => {
        form.show()
    })
}

async function create() {
    $('#addUser').click(async () => {
        event.preventDefault();
        let addUserForm = $('#addForm')
        let name = addUserForm.find('#name-new').val().trim();
        let surname = addUserForm.find('#surname-new').val().trim();
        let age = addUserForm.find('#age-new').val().trim();
        let email = addUserForm.find('#email-new').val().trim();
        let password = addUserForm.find('#password-new').val().trim();
        let thisRoles = () => {
            let tempArray = []
            let options = document.querySelector('#roles-field-new').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    tempArray.push(listRoles[i])
                }
            }
            return tempArray;
        }
        let user = {
            name: name,
            surname: surname,
            age: age,
            email: email,
            password: password,
            roles: thisRoles()
        }

        const response = await myFetch.saveUser(user);
        if (response.ok) {
            addUserForm.find('#name-new').val('');
            addUserForm.find('#surname-new').val('');
            addUserForm.find('#age-new').val('');
            addUserForm.find('#email-new').val('');
            addUserForm.find('#password-new').val('');
            addUserForm.find(thisRoles()).val('');
            $('.nav-tabs a[href="#nav-table"]').tab('show');
            await getAllUsers();
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert);
        }
    });
}
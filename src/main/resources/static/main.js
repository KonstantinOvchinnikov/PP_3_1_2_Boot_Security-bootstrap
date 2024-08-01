const myFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    authUser: async () => await fetch(`/user/user`),
    getAllUsers: async () => await fetch(`/admin/admin`),
    getUserById: async (id) => await fetch(`/admin/show?id=${id}`),
    deleteUser: async (id) => await fetch(`/admin/delete?id=${id}`),
    saveUser: async (user) => await fetch(`/admin/save`, {
        method: 'POST',
        headers: myFetch.head,
        body: JSON.stringify(user)
    }),
}


async function userInfo() {
    let temporary = '';
    const info = document.querySelector('#info');
    await myFetch.authUser()
        .then(res => res.json())
        .then(user => {
            let roles = user.roles.map(role => " " + role.role);
            temporary += `
              <p class="navbar-brand text-white">
                    <b><span class="align-middle">${user.email}</span></b>
                    <span class="align-middle">with roles: ${roles}</span>
              </p>
            `;
        });
    info.innerHTML = temporary;
}

async function getAuthUser() {
    let temporary = '';
    const tableUsers = document.querySelector('#tableUser tbody');
    await myFetch.authUser()
        .then(response => response.json())
        .then(user => {
            let roles = user.roles.map(role => " " + role.role);
            temporary = `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                </tr>
            `;
            tableUsers.innerHTML = temporary;
        })
}
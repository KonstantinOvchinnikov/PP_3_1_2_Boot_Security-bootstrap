let listRoles = [
    {id: 1, name: "USER"},
    {id: 2, name: "ADMIN"}]

$(async function () {
    await getAllUsers();
    await getAuthUser();
    await userInfo();
    await getNewUserForm();
    await create();
    await getDefaultModal();
})

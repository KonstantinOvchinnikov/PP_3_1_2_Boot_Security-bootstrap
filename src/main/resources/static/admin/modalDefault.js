async function getDefaultModal() {
    $('#defaultModal').modal({
        show: false,
        keyboard: true,
        backdrop: "static"

    }).on("show.bs.modal", (s) => {
        let thisModal = $(s.target);
        let userId = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'delete':
                formDeleteUser(thisModal, userId);
                break;
            case 'edit':
                formEditUser(thisModal, userId);
                break;
        }
    }).on("hidden.bs.modal", (s1) => {
        let thisModal = $(s1.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}
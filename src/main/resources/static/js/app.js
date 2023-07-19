document.addEventListener('DOMContentLoaded', function () {
    const quantityControls = document.getElementsByClassName('quantity-control');

    Array.from(quantityControls).forEach(function (control) {
        control.addEventListener('click', function () {
            const quantityInput = control.parentNode.querySelector('input[type="number"]');
            const action = control.getAttribute('data-action');

            let quantity = parseInt(quantityInput.value);
            if (action === 'increase') {
                quantity += 1;
            } else if (action === 'decrease') {
                if (quantity > 1) {
                    quantity -= 1;
                }
            }

            quantityInput.value = quantity;
        });
    });
});

function deleteItems() {
    var selectedItems = [];
    var checkboxes = document.getElementsByName('selectedItems');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedItems.push(checkboxes[i].value);
        }
    }

    if (selectedItems.length > 0 || document.getElementById('selectAll').checked) {
        if (confirm("Are you sure you want to delete the selected items?")) {
            document.getElementById("deleteForm").submit();
            // Gửi request xóa các sản phẩm đã chọn
            // Sử dụng AJAX hoặc submit form tùy theo yêu cầu của bạn
        }
    } else {
        alert("Please select at least one item to delete.");
    }
}

function toggleCheckbox(source) {
    var checkboxes = document.getElementsByName('selectedItems');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = source.checked;
    }
}
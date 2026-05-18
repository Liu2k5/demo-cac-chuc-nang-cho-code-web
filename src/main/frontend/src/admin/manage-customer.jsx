import { useState, useEffect } from "react";
import axios from "axios";

export default function ManageCustomer() {
    const [customers, setCustomers] = useState([]); // Khởi tạo mảng rỗng
    const [loading, setLoading] = useState(true);

    const handleRefresh = () => {
        // Gọi API bên trong useEffect để nó chỉ chạy 1 lần duy nhất khi load trang
        axios.get("/api/admin/manage-customer")
            .then(response => {
                setCustomers(response.data); // Axios trả dữ liệu về trong trường .data
                setLoading(false);
            })
            .catch(error => {
                console.error("Lỗi khi lấy dữ liệu:", error);
                setLoading(false);
            });
    };
    useEffect(() => handleRefresh, []); // Mảng rỗng [] đảm bảo code này chỉ chạy 1 lần

    const handleDelete = (event, username) => {
        event.preventDefault();
        axios.delete(`/api/admin/manage-customer/${username}`).then(handleRefresh);
    }

    const handleStatusChange = (event, username, isEnabled) => {
        event.preventDefault();
        const action = isEnabled ? 'disable' : 'enable';
        axios.put(`/api/admin/manage-customer/${username}/${action}`).then(handleRefresh);
    }

    return (
        <>
            <div>
                <h1>Danh sách khách hàng</h1>
                {loading ? (
                    <p>Đang tải dữ liệu...</p>
                ) : (
                    <ul>
                        {/* Dùng map để hiển thị danh sách vì customers là mảng */}
                        {customers.length > 0 ? (
                            customers.map((customer, index) => (
                                <li key={index}>{customer.username} - {customer.emailAddress}
                                    <form onSubmit={(event) => handleDelete(event, customer.username)} style={{ display: "inline" }}>
                                        <input type="hidden"/>
                                        <button type="submit">Xóa</button>
                                    </form>
                                    <form onSubmit={(event) => handleStatusChange(event, customer.username, customer.enabled)} style={{ display: "inline" }}>
                                        {customer.enabled ?
                                            <button type="submit">Khóa</button> :
                                            <button type="submit">Mở</button>
                                        }
                                    </form>
                                </li>
                            ))
                        ) : (
                            <p>Không có dữ liệu</p>
                        )}
                    </ul>
                )}
            </div>
        </>
    );
}
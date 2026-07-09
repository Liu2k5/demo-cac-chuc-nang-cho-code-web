import NavigationOptions from "../fragment/navigationOptions";
import {Route, Routes} from "react-router-dom";
import ManageCustomer from "../admin/manage-customer";
import Login from "./login";
import ProtectedRoute from "../fragment/ProtectedRoute";
import {useEffect, useState} from "react";
import axios from "axios";

export default function Home() {
    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        setLoading(true);
        axios.get("/api/auth/me").then((response) => {
            setUser(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
            // finally chờ các lệnh phía trên hoàn thành rồi thực hiện, bất kể xảy ra lỗi hay không
        }).finally(() => {
            setLoading(false);
        })
    }, [])

    // cần đợi cho việc tải dữ liệu hoàn tất, để tránh lỗi,
    // ví dụ ở đây là vấn đề admin bị đưa về trang login khi nhấn vào xem trang quản lí khách hàng
    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <>
            {/*<p>{user}</p>*/}
            <p>Vị trí của các tệp js trong cây thư mục tách biệt với đường dẫn trên trình duyệt</p>
            <p>Các chi tiết đều được gói trong các component, và các component được hiển thị
                khi là một trong các thành phần được truyền vào thuộc tính element của thẻ Route</p>
                
            <NavigationOptions/>
            <Routes>
                <Route path={"/"} element={
                    <>
                    </>
                }/>
                <Route path={"/admin/manage-customer"} element={
                    // <ProtectedRoute user={user} roleRequired={"ADMIN"}>
                        <ManageCustomer/>
                    // </ProtectedRoute>
                }/>
                <Route path={"/login"} element={
                    <Login/>
                }/>
            </Routes>
        </>
    );

}
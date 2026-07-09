import ManageCustomer from "../admin/manage-customer";
import {useNavigate} from "react-router";
import {Link, Route, Routes} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ProtectedRoute from "./ProtectedRoute";
import Orders from "../customer/orders";

export default function NavigationOptions() {
    const navigate = useNavigate();
    const [user, setUser] = useState({});
    useEffect(() => {
        axios.get("/api/auth/me").then((response) => {
            setUser(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [])

    const handleLogout = (e) => {
        e.preventDefault();
        axios.post("/api/auth/logout").then(() => {
            setUser(null);
            navigate("/login");
        })
    }

    return (
        <div
            style={{
                border: "1px solid black",
            }}
        >
            <h1>{user?.username}</h1>
            <h2>{user?.authorities?.map(o => o.authority)}</h2>

            <Routes>
                <Route path="/" element={
                    <div>
                        <h3>Bạn đương ở trang chủ</h3>
                        <div
                            style={{
                                height: "75px",
                                overflowY: "auto",
                                background: "lightgrey",
                            }}
                        >
                            <p>Trong React, không thể dùng trực tiếp thẻ a để chuyển trang</p>
                            <p>Các chi tiết này nằm trong thẻ Route, chỉ định tại một đường dẫn nào đó, một chi tiết tương ứng được hiển thị</p>
                            <p>Các thẻ Route được thẻ Routes bọc. Thẻ Routes chỉ chứa các thẻ Route</p>
                        </div>
                        <br/>
                        <p>{!user ?
                            <Link to="/login">Đăng nhập</Link> :
                            <Link to="#"
                                  onClick={handleLogout}
                            >Đăng xuất</Link>
                        } </p>
                        <p>
                            {/* <ProtectedRoute user={user} roleRequired={"ADMIN"}> */}
                                <Link to="/admin/manage-customer">Trang quản lí khách hàng</Link>
                            {/* </ProtectedRoute> */}
                        </p>
                        <p>
                            <Link to="/customer/orders">Danh sách đơn hàng</Link>
                        </p>
                    </div>
                } />
                <Route path="/customer/orders" element={<Orders/>} />
                <Route path="/admin/manage-customer" element={
                    <div>
                        <h3>Bạn đương ở trang quản lí khách hàng</h3>
                        <div
                            style={{
                                height: "75px",
                                overflowY: "auto",
                                background: "lightgrey",
                            }}
                        >
                            <p>Thẻ a gây tải lại toàn bộ nội dung trang</p>
                            <p>Thẻ Link được thiết kế riêng, đi kèm thẻ Route, được kết xuất thành thẻ a nhưng dược React thiết kế phù hợp với single page application</p>
                            <p>useNavigate() đơn giản là chuyển hướng người dùng đến một đường dẫn, được xử lí trong mã js, không phải html</p>
                        </div>
                        <br/>
                        <p>{!user ?
                            <Link to="/login">Đăng nhập</Link> :
                            <Link to="#"
                                  onClick={handleLogout}
                            >Đăng xuất</Link>                        
                            } </p>
                        <p>Chuyển về trang chủ: <a href="/" >dùng thẻ a</a> / <Link to="/">Dùng thẻ Link</Link> / <a href="#" onClick={() => navigate("/")}>dùng useNavigate() với sự kiện onClick()</a> </p>
                    </div>
                } />
                <Route path="/login" element={
                    <div>
                        <h3>Bạn đương ở trang đăng nhập</h3>
                        <br/>
                        <p><Link to="/">Chuyển về trang chủ</Link></p>
                    </div>
                } />
            </Routes>
        </div>
    );
}
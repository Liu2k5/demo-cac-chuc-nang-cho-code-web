import {Navigate} from "react-router";

// hàm bọc các đường dẫn cần bảo vệ, kiểm tra xem đã đăng nhập chưa và có quyền truy cập không
export default function ProtectedRoute({user, roleRequired, children }) {
    // 1. Kiểm tra nếu chưa đăng nhập
    if (!user || Object.keys(user).length === 0) {
        return <Navigate to="/login" replace />;
    }

    // 2. Kiểm tra role
    if (roleRequired) {
        const hasRole = user.authorities?.some(auth =>
            auth.authority === roleRequired || auth.authority === 'ROLE_' + roleRequired
        );

        if (!hasRole) {
            return <Navigate to="/403" replace />;
        }
    }

    // 3. Đúng quyền thì cho vào
    return children;
}
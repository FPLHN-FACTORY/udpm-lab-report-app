import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin-teacher-dashboard.css";
import {
  faChalkboardTeacher,
  faFilterCircleDollar,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import { Button, Col, Empty, Input, Row } from "antd";
import Image from "../../../helper/img/Image";
import { AdTeacherDashBoardAPI } from "../../../api/admin/AdTeacherDashBoardAPI";
import { useState } from "react";
import LoadingIndicator from "../../../helper/loading";

const AdminTeacherDashboard = () => {
  const [listTeacher, setListTeacher] = useState([]);
  const [loading, setLoading] = useState(false);
  const [name, setName] = useState("");

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Danh sách giảng viên | Lab-Report-App";
    fetchDataTeacher();
  }, []);

  const fetchDataTeacher = async () => {
    try {
      setLoading(true);
      let data = {
        name: name,
      };
      await AdTeacherDashBoardAPI.getAllTeacher(data).then((response) => {
        setListTeacher(response.data.data);
        setLoading(false);
      });
    } catch (error) {}
  };

  const handleSearch = async () => {
    fetchDataTeacher();
  };

  return (
    <>
      {loading && <LoadingIndicator />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faChalkboardTeacher}
              style={{ marginRight: "8px" }}
            />{" "}
            Danh sách giảng viên
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        <div className="box-son-general">
          <div className="" style={{ marginTop: 5 }}>
            <div style={{ flex: 1 }}>
              <Input
                type="text"
                placeholder="🔍 Nhập tên giảng viên"
                style={{ width: "40%" }}
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
              />
              <Button
                className="btn_filter"
                onClick={handleSearch}
                style={{ marginRight: "15px", marginLeft: "10px" }}
              >
                <FontAwesomeIcon
                  icon={faFilterCircleDollar}
                  style={{ marginRight: 5 }}
                />
                Tìm kiếm
              </Button>
            </div>
            <div className="header-teacher-dash-board">
              <Row
                style={{
                  display: "flex",
                  alignItems: "center",
                  minHeight: 60,
                }}
              >
                {" "}
                <Col span={1}>
                  <span style={{ marginLeft: 5 }}>#</span>{" "}
                </Col>
                <Col span={8}>
                  <span>Họ và tên</span>{" "}
                </Col>
                <Col span={7}>
                  <span>Tài khoản</span>{" "}
                </Col>
                <Col span={8}>
                  <span>Email</span>{" "}
                </Col>
              </Row>
            </div>
            <div>
              {listTeacher.length > 0 ? (
                listTeacher.map((item, index) => {
                  return (
                    <Row
                      style={{
                        display: "flex",
                        alignItems: "center",
                        minHeight: 60,
                      }}
                      className="item-list-member-factory__"
                    >
                      {" "}
                      <Col span={1}>
                        <span>{index + 1}</span>
                      </Col>
                      <Col
                        span={8}
                        style={{
                          display: "flex",
                          alignItems: "center",
                          flex: 1,
                        }}
                      >
                        <div
                          style={{
                            display: "flex",
                            alignItems: "center",
                          }}
                        >
                          <Image
                            url={item.picture}
                            picxel={28}
                            marginRight={-2}
                            name={item.name + " " + item.userName}
                          />
                          <span style={{ paddingLeft: "8px" }}>
                            {item.name}
                          </span>
                        </div>
                      </Col>
                      <Col span={7}>
                        <span>{item.userName}</span>
                      </Col>
                      <Col span={8}>
                        <span>{item.email}</span>
                      </Col>
                    </Row>
                  );
                })
              ) : (
                <Empty
                  style={{ paddingTop: "40px" }}
                  imageStyle={{ height: "60px" }}
                  description={<span>Không có dữ liệu</span>}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminTeacherDashboard;

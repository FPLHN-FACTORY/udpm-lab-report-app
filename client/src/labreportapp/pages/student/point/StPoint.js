import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-point.css";
import { faList, faMarker } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Select, Table } from "antd";
import { Option } from "antd/es/mentions";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { sinhVienCurrent } from "../../../helper/inForUser";
import { StPointAllAPI } from "../../../api/student/StPointAllAPI";

const StPoint = () => {
  const [listSemester, setListSemester] = useState([]);
  const [semester, setSemester] = useState("");
  const [listClass, setListClass] = useState([]);
  const [listPoint, setListPoint] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const getClass = (semester) => {
    const filter = {
      idStudent: sinhVienCurrent.id,
      idSemester: semester,
    };
    StPointAllAPI.getClassPointListByStudentInClassAndSemester(filter).then(
      (response) => {
        setListClass(response.data.data);
        setListPoint(response.data.data.points);
        setIsLoading(false);
      }
    );
  };

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
      setSemester(response.data.data[0].id);
    });
  };

  useEffect(() => {
    loadDataSemester();
    document.title = "Điểm | Lab-Report-App";
  }, []);

  useEffect(() => {
    setIsLoading(true);
    getClass(semester);
  }, [semester]);

  const handleChangeSemester = (e) => {
    setSemester(e);
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
    },
    {
      title: "Tên đầu điểm",
      dataIndex: "tenDauDiem",
      key: "tenDauDiem",
    },
    {
      title: "Trọng số",
      dataIndex: "trongSo",
      key: "trongSo",
    },
    {
      title: "Điểm",
      dataIndex: "diem",
      key: "diem",
    },
    {
      title: "Ghi chú",
      dataIndex: "ghiChu",
      key: "ghiChu",
    },
  ];

  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faMarker} style={{ marginRight: "8px" }} />{" "}
          Điểm
        </span>
      </div>
      <div className="filter-semester">
        <div className="wrapper-filter">
          <span style={{ marginLeft: "3px" }}>Học kỳ</span>
          <div className="select-semester" style={{ marginTop: "5px" }}>
            <Select
              style={{
                width: "100%",
                margin: "6px 0 10px 0",
              }}
              onChange={(e) => {
                handleChangeSemester(e);
              }}
              value={semester}
            >
              <Option value="">Chọn học kỳ</Option>
              {listSemester.length > 0 &&
                listSemester.map((item) => (
                  <Option value={item.id} key={item.id}>
                    {item.name}
                  </Option>
                ))}
            </Select>
          </div>
          <span style={{ marginLeft: "3px", fontSize: "13.5px" }}>
            Lựa chọn học kỳ để hiện thị chi tiết điểm
          </span>
        </div>
      </div>
      {listClass.map((item, index) => {
        return (
          <>
            <div className="table-attendence">
              <div
                className="header"
                style={{
                  padding: "10px",
                  fontSize: "18px",
                  fontWeight: "500",
                  display: "flex",
                }}
              >
                <span className="header-icon">
                  <FontAwesomeIcon icon={faList} />
                </span>
                <span className="header-title" style={{ marginLeft: "12px" }}>
                  Lớp {item.classCode}
                </span>
              </div>
              <div
                style={{
                  borderBottom: "1px solid #ddd",
                  marginTop: "10px",
                }}
              ></div>
              <Table
                columns={columns}
                dataSource={item.points}
                key="stt"
                pagination={false}
              />
            </div>
          </>
        );
      })}
    </div>
  );
};

export default StPoint;

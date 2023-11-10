import { Empty, Table, Tooltip } from "antd";
import Image from "../../../helper/img/Image";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";

const ItemTeacherDashboard = ({ item }) => {
  const columns = [
    {
      title: "#",
      dataIndex: "#",
      key: "#",
      align: "center",
      render: (text, record, index) => {
        return <span>{index + 1}</span>;
      },
    },
    {
      title: "Mã lớp",
      dataIndex: "code",
      key: "code",
      align: "center",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Level",
      dataIndex: "nameLevel",
      key: "nameLevel",
      align: "center",
      sorter: (a, b) => a.nameLevel.localeCompare(b.nameLevel),
    },
    {
      title: "Hoạt động",
      dataIndex: "nameActivity",
      align: "center",
      sorter: (a, b) => a.nameActivity.localeCompare(b.nameActivity),
      key: "nameActivity",
    },
    {
      title: "Sĩ số",
      dataIndex: "classSize",
      align: "center",
      sorter: (a, b) => a.classSize - b.classSize,
      key: "classSize",
    },
    {
      title: "Số bài viết",
      dataIndex: "numberPost",
      align: "center",
      sorter: (a, b) => a.numberPost - b.numberPost,
      key: "numberPost",
    },
    {
      title: "Số nhóm",
      dataIndex: "numberTeam",
      align: "center",
      sorter: (a, b) => a.numberTeam - b.numberTeam,
      key: "numberTeam",
    },
    {
      title: "Số buổi học",
      dataIndex: "numberMeeting",
      align: "center",
      sorter: (a, b) => a.numberMeeting - b.numberMeeting,
      key: "numberMeeting",
    },
    {
      title: "Số buổi đã học",
      dataIndex: "numberMeetingTookPlace",
      align: "center",
      sorter: (a, b) => a.numberMeetingTookPlace - b.numberMeetingTookPlace,
      key: "numberMeetingTookPlace",
    },
    {
      title: "Số sinh viên đạt",
      dataIndex: "numberStudentPass",
      align: "center",
      sorter: (a, b) => a.numberStudentPass - b.numberStudentPass,
      key: "numberStudentPass",
    },
    {
      title: "Số sinh viên trượt",
      dataIndex: "numberStudentFail",
      align: "center",
      sorter: (a, b) => a.numberStudentFail - b.numberStudentFail,
      key: "numberStudentFail",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      align: "center",
      render: (text, record) => (
        <div>
          <Tooltip title="Xem chi tiết">
            <Link to={`/admin/class-management/information-class/${record.id}`}>
              <FontAwesomeIcon
                style={{
                  marginRight: "15px",
                  cursor: "pointer",
                  color: "rgb(38, 144, 214)",
                }}
                icon={faEye}
                size="1x"
              />
            </Link>
          </Tooltip>
        </div>
      ),
    },
  ];

  return (
    <div style={{ marginBottom: 20 }}>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Image
          picxel={35}
          url={item.picture}
          name={item.userName}
          marginRight={8}
        />
        <span style={{ fontWeight: 500, fontSize: 16 }}>
          {item.name + " - " + item.userName + " /"}
        </span>{" "}
        <span
          style={{ fontWeight: 500, fontSize: 16, marginLeft: 5, color: "red" }}
        >
          Tổng số lớp: {item.listClass.length}
        </span>
      </div>
      <div style={{ marginTop: 10 }}>
        <span>Danh sách lớp:</span> <br />
      </div>
      <div style={{ marginTop: 8 }}>
        {item.listClass.length > 0 && (
          <Table
            className="table-item-teacher-dashboard"
            dataSource={item.listClass}
            columns={columns}
            pagination={false}
          />
        )}
        {item.listClass.length === 0 && (
          <>
            <p
              style={{
                textAlign: "center",
                marginTop: "100px",
                fontSize: "15px",
                color: "red",
              }}
            >
              <Empty
                imageStyle={{ height: 60 }}
                description={<span>Không có dữ liệuc</span>}
              />{" "}
            </p>
          </>
        )}
      </div>
    </div>
  );
};
export default ItemTeacherDashboard;

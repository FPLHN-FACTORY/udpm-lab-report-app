import { Modal, Row, Col, Input, Table, Image, Spin, Select } from "antd";
import "./styleModalDetailProject.css";
import { useEffect, useState } from "react";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import moment from "moment";
import { MemberProjectManagementAPI } from "../../../../api/admin/project-management/memberProjectManagement.api";
import { CategoryProjectManagementAPI } from "../../../../api/admin/project-management/categoryProjectManagement.api";
import { AdPotalsRoleProjectAPI } from "../../../../api/admin/role-project/AdPotalsRoleProject.api";

const { TextArea } = Input;
const { Option } = Select;
const ModalDetailProject = ({ visible, onCancel, idProject }) => {
  const [project, setProject] = useState({});
  const [listData, setListData] = useState([]);
  const [listCategoryProjects, setListCategoryProjects] = useState("");
  const [listRoleProject, setListRoleProject] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (idProject !== null && idProject !== "" && visible === true) {
      setListData([]);
      featchRoleProject(idProject);
      fetchData();
    }
  }, [visible, idProject, onCancel]);

  const featchRoleProject = async (idProject) => {
    try {
      await AdPotalsRoleProjectAPI.getAllRoleProjectByProjId(idProject).then(
        (response) => {
          setListRoleProject(response.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      await ProjectManagementAPI.detail(idProject).then((response) => {
        setProject(response.data.data);
      });
      const responeListCategoryProject =
        await CategoryProjectManagementAPI.fetchCategoryWithIdProject(
          idProject
        );
      let arrayCategory = "";
      let checkCategory = 0;
      await responeListCategoryProject.data.data.map((data) => {
        checkCategory = ++checkCategory;
        if (checkCategory > 1) {
          arrayCategory =
            arrayCategory + ", " + data.nameCategory.toLowerCase();
        } else {
          arrayCategory = data.nameCategory;
        }
        setListCategoryProjects(arrayCategory);
      });

      const responeListMemberProject =
        await MemberProjectManagementAPI.getAllStudentXuong(idProject);
      const listMemberProject = await responeListMemberProject.data.data;
      setListData(listMemberProject);
      setLoading(false);
    } catch (err) {
      console.log(err);
    }
  };

  const setStt = (current) => {
    return current;
  };
  const data = listData;
  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
    },
    {
      title: "Mã",
      dataIndex: "userName",
      key: "userName",
      sorter: (a, b) => a.userName.localeCompare(b.userName),
    },
    {
      title: "Thành viên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        return (
          <div>
            <Image.PreviewGroup>
              <Image
                src={record.picture}
                alt="Avatar"
                width={25}
                height={25}
                marginRight={-5}
                className="avatarMember"
              />
            </Image.PreviewGroup>
            <span style={{ paddingLeft: "8px" }}>{record.name}</span>
          </div>
        );
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Vai trò",
      dataIndex: "idRole",
      key: "idRole",
      render: (text, record) =>
        listRoleProject.length > 0 ? (
          <Select defaultValue={text}>
            {listRoleProject.map((item) => (
              <Option value={item.id}>{item.name}</Option>
            ))}
          </Select>
        ) : (
          "Không có vai trò để chọn"
        ),
    },
  ];
  return (
    <Modal
      onCancel={onCancel}
      visible={visible}
      width={1150}
      footer={null}
      style={{ top: "53px" }}
    >
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Chi tiết dự án</span>
      </div>
      <div style={{ marginTop: "15px" }}>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={12}>
            <span>Mã dự án:</span> <br />
            <Input value={project.code} type="text" readOnly />
          </Col>
          <Col span={12}>
            <span>Tên dự án:</span> <br />
            <Input type="text" value={project.name} readOnly />
          </Col>
        </Row>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={24}>
            <span>Thể loại:</span>
            <Input type="text" value={listCategoryProjects} readOnly />
          </Col>
        </Row>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={12}>
            <span>Thời gian bắt đầu:</span> <br />
            <Input
              value={moment(project.startTime).format("YYYY-MM-DD")}
              type="date"
              readOnly
            />
          </Col>
          <Col span={12}>
            <span>Thời gian kết thúc:</span> <br />
            <Input
              value={moment(project.endTime).utcOffset(7).format("YYYY-MM-DD")}
              type="date"
              readOnly
            />
          </Col>
        </Row>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={12}>
            <span>Tiến độ: {project.progress + "%"}</span> <br />
            <div className="progress-block">
              <div
                className="progress-bar"
                style={{ width: project.progress + "%" }}
              ></div>
            </div>
          </Col>
          <Col span={12}>
            <span>Trạng thái:</span> <br />
            <Input
              value={
                project.statusProject === "DA_DIEN_RA"
                  ? "Đã diễn ra"
                  : project.statusProject === "DANG_DIEN_RA"
                  ? "Đang diễn ra"
                  : "Chưa diễn ra"
              }
              readOnly
            />
          </Col>
        </Row>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={24}>
            <span>Mô tả:</span> <br />
            <TextArea rows={2} value={project.descriptions} readOnly />
          </Col>
        </Row>
        <Row gutter={16}>
          <Col span={24}>
            <span>Thành viên:</span>
          </Col>
        </Row>
        <Row>
          {loading ? (
            <div className="loading-overlay">
              <Spin size="large" />
            </div>
          ) : (
            <Table
              style={{ width: "100%" }}
              dataSource={data.map((item, index) => ({
                ...item,
                stt: setStt(index + 1),
              }))}
              rowKey="stt"
              columns={columns}
              pagination={{
                pageSize: 5,
                showSizeChanger: false,
                size: "small",
              }}
            />
          )}
        </Row>
      </div>
    </Modal>
  );
};

export default ModalDetailProject;

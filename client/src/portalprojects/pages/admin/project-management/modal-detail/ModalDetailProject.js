import { Modal, Row, Col, Input, Table, Spin, Select, DatePicker } from "antd";
import "./styleModalDetailProject.css";
import { useEffect, useState } from "react";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import { MemberProjectManagementAPI } from "../../../../api/admin/project-management/memberProjectManagement.api";
import { CategoryProjectManagementAPI } from "../../../../api/admin/project-management/categoryProjectManagement.api";
import dayjs from "dayjs";
import { AdGroupProjectAPI } from "../../../../../labreportapp/api/admin/AdGroupProjectAPI";
import Image from "../../../../helper/img/Image";
const { RangePicker } = DatePicker;
const { TextArea } = Input;

const ModalDetailProject = ({ visible, onCancel, idProject }) => {
  const [project, setProject] = useState({});
  const [listData, setListData] = useState([]);
  const [listCategoryProjects, setListCategoryProjects] = useState("");
  const [loading, setLoading] = useState(false);
  const [selectedGroupProject, setSelectedGroupProject] = useState(null);
  const [listGroupProject, setListGroupProject] = useState([]);

  useEffect(() => {
    if (idProject !== null && idProject !== "" && visible === true) {
      setListData([]);
      featDataGroupProject();
      fetchData();
    }
  }, [visible, idProject, onCancel]);

  const featDataGroupProject = async () => {
    try {
      await AdGroupProjectAPI.getAllGroupToProjectManagement().then(
        (response) => {
          setListGroupProject(response.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      await ProjectManagementAPI.detailUpdate(idProject).then((response) => {
        setProject(response.data.data);
        setSelectedGroupProject(response.data.data.groupProjectId);
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
      title: "#",
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
          <div style={{ display: "flex", alignItems: "center" }}>
            <Image
              url={record.picture}
              picxel={25}
              marginRight={8}
              name={record.name}
            />
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
      dataIndex: "listRoleSelect",
      key: "listRoleSelect",
      render: (text, record) =>
        record.listRole != null ? (
          <Select
            mode="multiple"
            placeholder="Chọn vai trò"
            value={record.listRole.map((item) => item.nameRole)}
            style={{
              width: "100%",
            }}
            maxTagCount={3}
            key={record.idRoleMemberProject}
            options={record.listRole.map((item) => ({
              value: item.idRole,
              label: item.nameRole,
              disabled: true,
            }))}
          />
        ) : (
          "Chưa có vai trò "
        ),
    },
  ];
  return (
    <Modal
      onCancel={onCancel}
      open={visible}
      width={1150}
      footer={null}
      style={{ top: "53px" }}
    >
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Chi tiết dự án</span>
      </div>
      {loading ? (
        <div
          className="loading-overlay"
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            minHeight: "540px",
          }}
        >
          <Spin size="large" style={{ paddingTop: "50px" }} />
        </div>
      ) : (
        <div style={{ marginTop: "15px" }}>
          <Row gutter={24} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span className="notBlank">(*) </span>
              <span>Mã dự án:</span> <br />
              <Input value={project.code} type="text" readOnly />
            </Col>
            <Col span={12}>
              <span className="notBlank">(*) </span>
              <span>Tên dự án:</span> <br />
              <Input type="text" value={project.name} readOnly />
            </Col>
          </Row>
          <Row gutter={24} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span className="notBlank">(*) </span>
              <span>Thể loại:</span>
              <Input type="text" value={listCategoryProjects} readOnly />
            </Col>
          </Row>
          <Row gutter={24} style={{ marginBottom: "10px" }}>
            <Col span={12}>
              <span className="notBlank">(*) </span>
              <span>Thời gian:</span> <br />
              <RangePicker
                style={{ width: "100%" }}
                format="YYYY-MM-DD"
                value={[
                  project.startTime ? dayjs(project.startTime) : null,
                  project.endTime ? dayjs(project.endTime) : null,
                ]}
                changeOnBlur
              />
            </Col>
            <Col span={12}>
              <span>Nhóm dự án:</span>
              <Select
                showSearch
                style={{ width: "100%" }}
                placeholder="Chọn thuộc nhóm dự án"
                optionFilterProp="children"
                filterOption={(input, option) =>
                  (option?.label ?? "").includes(input)
                }
                filterSort={(optionA, optionB) =>
                  (optionA?.label ?? "")
                    .toLowerCase()
                    .localeCompare((optionB?.label ?? "").toLowerCase())
                }
                value={selectedGroupProject}
                defaultValue={selectedGroupProject}
                options={[
                  { value: null, label: "Không trong nhóm dự án" },
                  ...listGroupProject.map((i) => {
                    return { value: i.id, label: i.name };
                  }),
                ]}
              />
            </Col>
          </Row>
          <Row gutter={24} style={{ marginBottom: "15px" }}>
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
          </Row>
        </div>
      )}
    </Modal>
  );
};

export default ModalDetailProject;

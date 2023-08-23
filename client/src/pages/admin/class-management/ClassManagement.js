import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCogs,
  faPencil,
  faPlus
} from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Select,
  Row
} from "antd";
import LoadingIndicator from "../../../helper/loading";
import { SetTeacherSemester } from "../../../app/admin/ClassManager.reducer";

import { useEffect, useState } from "react";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
import "./style-class-management.css";
import { list } from "@chakra-ui/react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ModalCreateProject from "../../admin/class-management/create-class/ModalCreateClass";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
const ClassManagement = () => {
  const { Option } = Select;
  const [listClassAll, setlistClassAll] = useState([]); //getAll
  const [teacherDataAll, setTeacherDataAll] = useState([]); // Dữ liệu teacherId và username
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");

  const [selectedItems, setSelectedItems] = useState([]);
  const [code, setCode] = useState('');
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [loading, setLoading] = useState(true);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [clear, setClear] = useState(false);

  const dispatch = useAppDispatch();
const listClassPeriod = [];

  for (let i = 1; i <= 10; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    document.title = "Bảng điều khiển";
    setIdSemesterSearch("");
    featchAllMyClass();

  }, []);

  useEffect(() => {
    featchAllMyClass();
  }, [current]);
  const featchAllMyClass = async () => {
    setLoading(false);
    let filter = {
      idTeacher: selectedItemsPerson,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
      code: code,
      classPeriod: selectedItems,
      page: current,
      size: 10,
    };

    try {
      await ClassAPI.getAllMyClass(filter).then((respone) => {
        console.log(selectedItemsPerson);
        setTotalPages(parseInt(respone.data.data.totalPages));
        setlistClassAll(respone.data.data.data);
        console.log(respone.data.data.data);

        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
    }
  };
  useEffect(() => {
    featchAllMyClass();
    setClear(false);
  }, [clear]);

  useEffect(() => {
    const fetchTeacherData = async () => {
      const responseTeacherData = await ClassAPI.fetchAllTeacher();
      const teacherData = responseTeacherData.data;
      setTeacherDataAll(teacherData);
    };
    fetchTeacherData();
  }, []);
  useEffect(() => {
    const featchDataActivity = async (idSemesterSeach) => {
      console.log(idSemesterSeach);
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            setActivityDataAll(respone.data.data);
            setLoading(true);
          }
        );

    };
    featchDataActivity(idSemesterSeach);
  }, [idSemesterSeach]);
  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        setLoading(false);
        const responseClassAll =
        await ClassAPI.fetchAllSemester();
      const listClassAll = responseClassAll.data;
          dispatch(SetTeacherSemester(listClassAll.data));
          if (listClassAll.data.length > 0) {
            setIdSemesterSearch(listClassAll.data[0].id);
          } else {
            setIdSemesterSearch("null");
          }
          setSemesterDataAll(listClassAll.data);
          setLoading(true);
    
      } catch (error) {
        alert("Vui lòng F5 lại trang !");
      }
    };
    featchDataSemester();
  }, []);
  
  useEffect(() => {
    document.title = "Quản lý lớp | Portal-Projects";
    setCode("");
    setSelectedItems("");
    setSelectedItemsPerson("");
  }, []);



  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      width: "3%",
    },
    {
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Tên Lớp",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.username.localeCompare(b.username),
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      render: (text, record) => {
        const startTime = new Date(record.startTime);

        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;
       

        return (
          <span>
            {formattedStartTime}
          </span>
        );
      },
    },
    {
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod.localeCompare(b.classPeriod),
    },
    {
      title: "Sĩ số",
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize.localeCompare(b.classSize),
    },
    {
      title: "Giáo viên",
      dataIndex: "teacherId",
      key: "teacherId",
      sorter: (a, b) => a.teacherId.localeCompare(b.teacherId),
      render: (text, record) => {
        const teacher = teacherDataAll.find((item) => item.id === record.teacherId);
        return teacher ? teacher.username : "";
      },
    },
    {
      title: "Hoạt động",
      dataIndex: "activityName",
      key: "activityName",
      sorter: (a, b) => a.activityName.localeCompare(b.activityName),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      
      // render: (text,record) => (
      //   <div>
      //     <Tooltip title="Cập nhật">
      //       <FontAwesomeIcon
      //         onClick={() => {
      //           handlePeriodUpdate(record.id);
      //         }}
      //         style={{ marginRight: "15px", cursor: "pointer" }}
      //         icon={faPencil}
      //         size="1x"
      //       />
      //     </Tooltip>
      //   </div>
      // ),
    },
  ];
  
  const handleSearchAllByFilter = async () => {
    await featchAllMyClass();
    toast.success("Tìm kiếm thành công !");
  };
const handleCodeChange = (e) => {
  setCode(e.target.value);
};
const handleSelectChange = (value) => {
  setSelectedItems(value);
};

const handleSelectPersonChange = (value) => {
  setSelectedItemsPerson(value);
};



const handleClear = () => {
  if (semesterDataAll.length > 0) {
    setIdSemesterSearch(semesterDataAll[0].id);
  } else {
    setIdSemesterSearch("null");
  }
  setSelectedItems("");
  setCode("");
  setIdActivitiSearch("");
  setSelectedItemsPerson("");
  toast.success("Hủy bộ lọc thành công !");
  setClear(true);
};
const [showCreateModal, setShowCreateModal] = useState(false);

  const handleModalCreateCancel = async() => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(false);
    await featchAllMyClass();

  };

  return (
    <div className="class_management">
            {!loading && <LoadingIndicator />}

      <div className="title_class">
        {" "}
        <FontAwesomeIcon icon={faCogs} size="1x" />
        <span style={{ marginLeft: "10px" }}>Quản lý lớp học</span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content">
          <div className="content-wrapper">
            <Row className="selectPriodAndPerson">
            <div >
              <span style={{marginLeft:'20px'}}>Ca Học:</span>
            <QuestionCircleFilled
                style={{ paddingLeft: "5px", fontSize: "15px" }}
              />
              <br/>
              <Select
                  showSearch
                  placeholder="Select a class Period"
                  value={selectedItems}
                  onChange={handleSelectChange}
                  style={{ width: '270px' , height: '50%',marginLeft:'20px'}}
                  >
                      <Option value="">Tất Cả</Option>
                      {listClassPeriod.map((value) => {
                  return (
                    <Option value={value} key={value}>
                      {value}
                    </Option>
                  );
                })}
              </Select>
            </div>
            <div className="selectSearch2" >
            <span style={{marginLeft:'20px'}}>GVHD:</span>
            <QuestionCircleFilled
                style={{ paddingLeft: "5px", fontSize: "15px" }}
              />
              <br/>
              <Select
                  showSearch
                  placeholder="Select a person "
                  value={selectedItemsPerson}
                  onChange={handleSelectPersonChange}
                style={{ width: '270px', height: '50%',marginLeft:'20px'}}
                   >
                      <Option value="">Tất cả</Option>

                {teacherDataAll.map((teacher) => (
                  <Option key={teacher.id} value={teacher.id}>
                    {teacher.username}
                  </Option>
                ))}
              </Select>
              </div>
                        
            </Row>
          </div>
          
        </div>
        <Row className="selectActiAndSemes">
        <div >
        <span style={{marginLeft:'20px'}}>Semester:</span>
            <QuestionCircleFilled
                style={{ paddingLeft: "5px", fontSize: "15px" }}
              />
              <br/>
              <Select
                  showSearch
                  placeholder="Select a person "
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                style={{ width: '270px', height: '50%',marginLeft:'20px' }}
                   >

                      <Option value="">Tất cả</Option>

                {semesterDataAll.map((semester) => (
                  <Option key={semester.id} value={semester.id}>
                    {semester.name}
                  </Option>
                ))}
              </Select>
              </div>
              <div className="selectSearch3" >

              <span style={{marginLeft:'20px'}}>Hoạt Động:</span>
            <QuestionCircleFilled
                style={{ paddingLeft: "5px", fontSize: "15px" }}
              />
              <br/>
              <Select
                  showSearch
                  placeholder="Select a activity "
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                style={{ width: '270px', height: '50%',marginLeft:'20px'}}
              >
                      <Option value="">Tất cả</Option>
                        {activityDataAll.map((activity) => (
                      <Option key={activity.id} value={activity.id}>
                               {activity.name}
                      </Option>
                ))}
              </Select>
              </div>
              </Row>
              <Row>
              <div className="inputCode">
              <span style={{marginLeft:'20px'}}>Mã Lớp:</span>
            <QuestionCircleFilled
                style={{ paddingLeft: "5px", fontSize: "15px" }}
              />
              <br/>
              <Input
                placeholder="Import Class Code"
                type="text"
                value={code}
                
                onChange={handleCodeChange}

                style={{ width: "780px" ,marginLeft:'20px'}}
              />
            </div>
              </Row>
              <div className="box_btn_filter">
            <Button className="btn_filter" 
            onClick={handleSearchAllByFilter}
            >
              Tìm kiếm
            </Button>
            <Button className="btn_clear" 
            onClick={handleClear}
            >
              Làm mới
            </Button>
          </div>
      </div>

      <div className="table_Allstakeholder_management">
        <div className="title_stakeholder_management">
          {" "}
          <FontAwesomeIcon icon={faCogs} size="1x" />
          <span style={{ fontSize: "18px", fontWeight: "500",marginBottom:"-50px" }}>
            {" "}
            Danh sách lớp học
          </span>
          <div className="createButton">
            <Button 
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
                
              }}
              onClick={() => {
                setShowCreateModal(true);
              }}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Thêm Lớp
            </Button>
          </div>
          
      </div>
      
        </div>
        
        <div style={{ marginTop: "25px" }}>
        <div>
          {listClassAll.length > 0 ? (
            <>
              <div className="table">
                <Table
                  dataSource={listClassAll}
                  rowKey="id"
                  columns={columns}
                  pagination={false}
                />
              </div>
              <div className="pagination_box">
                <Pagination
                  simple
                  current={current}
                  onChange={(value) => {
                    setCurrent(value);
                  }}
                  total={totalPages * 10}
                />
              </div>
            </>
          ) : (
            <>
              <p
                style={{
                  textAlign: "center",
                  marginTop: "100px",
                  fontSize: "15px",
                }}
              >
                Không có lớp học
              </p>
            </>
          )}
        </div>
        </div>
        
      <ModalCreateProject
          visible={showCreateModal}
          onCancel={handleModalCreateCancel}
          />
    </div>
  );
};

export default ClassManagement;

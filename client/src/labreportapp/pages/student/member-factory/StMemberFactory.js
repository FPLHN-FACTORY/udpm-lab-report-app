import { faPeopleGroup } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const StMemberFactory = () => {
  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>
          Danh sách thành viên trong xưởng
        </span>
      </div>

      <div
        className="box-son-general"
        style={{ minHeight: "620px", marginTop: 20 }}
      >
        <div
          className="member-factory-managment"
          style={{ marginBottom: "20px" }}
        >
          <div style={{}}>Danh sách thành viên trong xưởng:</div>
        </div>
      </div>
    </div>
  );
};

export default StMemberFactory;

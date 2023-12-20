import HeaderDetailProject from "./HeaderDetailProject";
import { useParams } from "react-router-dom";
import Board from "./board/Board";
import "./styleDetailProject.css";
import { DetailProjectAPI } from "../../../api/detail-project/detailProject.api";
import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  SetProject,
  SetLoading,
  GetProject,
} from "../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { SetMemberProject } from "../../../app/reducer/detail-project/DPMemberProject.reducer";
import {
  GetMemberPeriod,
  GetPeriodCurrent,
  SetMemberPeriod,
  SetPeriodCurrent,
} from "../../../app/reducer/detail-project/DPPeriodSlice.reducer";
import {
  SetBoard,
  SetFilter,
} from "../../../app/reducer/detail-project/DPBoardSlice.reducer";
import { useLocation, useNavigate } from "react-router-dom";
import {
  connectStompClient,
  getStompClient,
} from "./stomp-client-config/StompClientManager";
import { SetLabelProject } from "../../../app/reducer/detail-project/DPLabelProject.reducer";
import Table from "./table/Table";
import { useState } from "react";
import { SetDetailTodo } from "../../../app/reducer/detail-project/DPDetailTodoSlice.reducer";
import TaskModal from "./task-modal/TaskModal";
import { SetMeRoleProject } from "../../../app/reducer/detail-project/DPRoleProjectSlice.reducer";
import logoUdpm3 from "../../../../labreportapp/assets/img/logo-udpm-3.png";
import logoUdpm2 from "../../../../labreportapp/assets/img/logo-udpm-2.png";
import {
  GetCheckRole,
  SetCheckRole,
} from "../../../app/reducer/detail-project/DPDetailProjectCheckRole.reducer";

const DetailProject = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();

  useEffect(() => {
    DetailProjectAPI.checkMemberProject(id).then(
      (response) => {},
      (error) => {}
    );
  }, []);

  useEffect(() => {
    handleCheckRole();
  }, []);

  const handleCheckRole = () => {
    DetailProjectAPI.checkRole(id).then(
      (response) => {
        dispatch(SetCheckRole(response.data.data));
      },
      (error) => {}
    );
  };

  const checkRole = useAppSelector(GetCheckRole);
  console.log(checkRole);

  const loadData = () => {
    document.querySelector(".logo_project").src = logoUdpm2;
    DetailProjectAPI.findProjectById(id)
      .then((response) => {
        dispatch(SetProject(response.data.data));
        document.title = response.data.data.name + " | Portal-Projects";
      })
      .finally(() => {
        dispatch(SetLoading(false));
      });
  };

  const loadDataPeriod = () => {
    DetailProjectAPI.findAllPeriodProject(id).then((response) => {
      dispatch(SetMemberPeriod(response.data.data));
    });
  };

  const loadDataRoleProject = () => {
    DetailProjectAPI.getAllRoleProject(id).then((response) => {
      dispatch(SetMeRoleProject(response.data.data));
    });
  };

  useEffect(() => {
    dispatch(SetLoading(true));
    loadData();
    loadDataPeriod();
    loadDataRoleProject();

    return () => {
      document.querySelector("body").style.backgroundColor = "";
      document.querySelector("body").style.backgroundImage = "url()";
      dispatch(SetProject([]));
      dispatch(SetMemberProject([]));
      dispatch(SetMemberPeriod([]));
      dispatch(SetBoard([]));
      dispatch(SetPeriodCurrent(null));
      dispatch(
        SetFilter({
          name: "",
          member: [],
          label: [],
          dueDate: [],
        })
      );
      if (document.querySelector(".logo_project") != null) {
        document.querySelector(".logo_project").src = logoUdpm3;
      }
    };
  }, [id]);

  const listPeriod = useAppSelector(GetMemberPeriod);
  const periodCurrent = useAppSelector(GetPeriodCurrent);
  const detailProject = useAppSelector(GetProject);

  useEffect(() => {
    if (listPeriod != null && listPeriod.length > 0) {
      const searchParams = new URLSearchParams(location.search);
      const idPeriod = searchParams.get("idPeriod");

      if (idPeriod != null && idPeriod !== "") {
        const itemWithStatusOne = listPeriod.find(
          (item) => item.id === idPeriod
        );
        dispatch(SetPeriodCurrent(itemWithStatusOne));
      } else {
        if (listPeriod.some((item) => item.status === 1)) {
          const itemWithStatusOne = listPeriod.find(
            (item) => item.status === 1
          );
          dispatch(SetPeriodCurrent(itemWithStatusOne));
        } else if (listPeriod.length > 0) {
          const lastItem = listPeriod[listPeriod.length - 1];
          dispatch(SetPeriodCurrent(lastItem));
        }
      }
    }
  }, [listPeriod]);

  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (periodCurrent != null && Object.keys(periodCurrent).length > 0) {
      const searchParams = new URLSearchParams(location.search);
      searchParams.set("idPeriod", periodCurrent.id);
      const newSearch = searchParams.toString();
      const newPath = `${location.pathname}?${newSearch}`;
      navigate(newPath, { replace: true });
      connectStompClient(dispatch);
    }

    return () => {
      if (
        periodCurrent !== null &&
        periodCurrent !== {} &&
        Object.keys(periodCurrent).length > 0
      ) {
        getStompClient().disconnect();
      }
    };
  }, [periodCurrent]);

  useEffect(() => {
    if (periodCurrent == null) {
      connectStompClient(dispatch);
    } else if (Object.keys(periodCurrent).length === 0) {
      connectStompClient(dispatch);
    }
    return () => {
      if (periodCurrent == null) {
        getStompClient().disconnect();
      } else if (Object.keys(periodCurrent).length === 0) {
        getStompClient().disconnect();
      }
    };
  }, [periodCurrent]);

  useEffect(() => {
    if (id != null) {
      loadDataLabelProject();
    }
  }, [id]);

  const loadDataLabelProject = () => {
    DetailProjectAPI.fetchAllLabelProject(id).then((response) => {
      dispatch(SetLabelProject(response.data.data));
    });
  };

  const [showDetailModal, setShowDetailModal] = useState(false);
  const [idTodoSelected, setIdTodoSelected] = useState("");

  const handleModalCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowDetailModal(false);
    dispatch(SetDetailTodo(null));
    setIdTodoSelected("");

    const searchParams = new URLSearchParams(location.search);
    searchParams.delete("idTodo");
    const newSearch = searchParams.toString();
    const newPath = newSearch
      ? `${location.pathname}?${newSearch}`
      : location.pathname;
    navigate(newPath, { replace: true });

    document.title = detailProject.name + " | Portal-Projects";
  };

  const searchParams = new URLSearchParams(location.search);
  const idTodo = searchParams.get("idTodo");

  useEffect(() => {
    if (idTodo != null) {
      setShowDetailModal(true);
      setIdTodoSelected(idTodo);
      document.querySelector("body").style.overflowX = "hidden";
    }
  }, [idTodo]);

  const isTableVisible = location.pathname.includes("/table");

  return (
    <div className="detail-project">
      <div>
        {" "}
        <HeaderDetailProject />
      </div>
      <div className="board-container">
        <div
          className="board-style"
          style={{ display: isTableVisible ? "none" : "" }}
        >
          <Board />
        </div>
      </div>
      <div className="table-container-custom">
        {" "}
        <div
          className="custom-table-style"
          style={{ display: isTableVisible ? "" : "none" }}
        >
          {" "}
          <Table />
        </div>
      </div>
      <TaskModal
        open={showDetailModal}
        onCancel={handleModalCancel}
        id={idTodoSelected}
      />
    </div>
  );
};

export default DetailProject;
